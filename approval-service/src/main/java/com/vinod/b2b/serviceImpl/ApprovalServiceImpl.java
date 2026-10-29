package com.vinod.b2b.serviceImpl;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.vinod.b2b.dto.ApprovalDecisionDto;
import com.vinod.b2b.dto.UserDto;
import com.vinod.b2b.entity.ApprovalDecisions;
import com.vinod.b2b.entity.ApprovalWorkflow;
import com.vinod.b2b.exceptions.PurchaseRequestNotFoundException;
import com.vinod.b2b.feignclients.UserServiceFeignClient;
import com.vinod.b2b.kafka.EventType;
import com.vinod.b2b.kafka.KafkaPurchaseApprovalProducer;
import com.vinod.b2b.kafka.PurchaseRequestDecisionEvent;
import com.vinod.b2b.repository.ApprovalRepository;
import com.vinod.b2b.repository.ApprovalWorkFlowRepository;
import com.vinod.b2b.service.ApprovalService;
import com.vinod.b2b.utility.ApprovalLevel;
import com.vinod.b2b.utility.Decision;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {
	private final ApprovalRepository repository;
	private final UserServiceFeignClient feignClient;
	private final KafkaPurchaseApprovalProducer kafkaPurchaseApprovalProducer;
	private final ApprovalWorkFlowRepository approvalWorkFlowRepository;
	private static final List<ApprovalLevel> APPROVAL_FLOW = List.of(ApprovalLevel.MANAGER, ApprovalLevel.FINANCE,
			ApprovalLevel.PROCUREMENT_OFFICER);

	private Set<String> authorities(Authentication authentication) {
		if (authentication == null)
			return Set.of();
		return authentication.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toSet());
	}

	private boolean has(Set<String> authorities, String permission) {
		return authorities.contains(permission);
	}

	private void requirePermission(Set<String> authorities, String permission) {
		if (!has(authorities, permission)) {
			throw new AccessDeniedException("Missing permission: " + permission);
		}
	}

	private ApprovalLevel getCurrentRole(Authentication authentication) {
		if (authentication == null) {
			throw new AccessDeniedException("User is not authenticated");
		}
		String role = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.filter(authority -> authority.startsWith("ROLE_")).map(authority -> authority.substring(5)).findFirst()
				.orElseThrow(() -> new AccessDeniedException("No role found for user"));
		try {
			return ApprovalLevel.valueOf(role);
		} catch (IllegalArgumentException e) {
			throw new AccessDeniedException("User role does not match an ApprovalLevel");
		}
	}

	private UUID resolveUserId(String username) {
		UserDto user = feignClient.findByName(username);
		if (user == null || user.getUserId() == null) {
			throw new RuntimeException("User Not found by username: " + username);

		}
		return user.getUserId();
	}

	@Override
	public ApprovalDecisions addApporvalDecision(UUID prId, ApprovalDecisionDto approvalDecisionDto,
			Authentication authentication) {
		Set<String> permissions = authorities(authentication);
		UUID approverId = resolveUserId(authentication.getName());
		if (!has(permissions, "APPROVAL_DECIDE")) {

			throw new RuntimeException("You do not have permission to approve");
		}
		Decision decision = approvalDecisionDto.getDecision();

		ApprovalLevel currentRole = getCurrentRole(authentication);
		ApprovalLevel requiredLevel = getRequiredApprovalLevel(prId);
		if (currentRole != requiredLevel) {
			throw new RuntimeException(requiredLevel + " : approval required ");
		}
		boolean alreadyApproved = repository.existsByPrIdAndDecisionAndLevel(prId, Decision.APPROVED, currentRole);

		boolean alreadyRejected = repository.existsByPrIdAndDecisionAndLevel(prId, Decision.REJECTED, currentRole);

		if (alreadyApproved || alreadyRejected) {
			throw new IllegalStateException("A decision has already been recorded for this level: " + currentRole);
		}

		ApprovalDecisions approvalDecision = new ApprovalDecisions();
		approvalDecision.setPrId(prId);
		approvalDecision.setApproverId(approverId);
		approvalDecision.setDecision(decision);
		approvalDecision.setLevel(currentRole);
		approvalDecision.setReason(approvalDecisionDto.getReason());
		ApprovalDecisions savedDecision = repository.save(approvalDecision);
		ApprovalWorkflow workflow = approvalWorkFlowRepository.findById(prId)
				.orElseThrow(() -> new RuntimeException("Purchase request not found for id: " + prId));
		workflow.setCurrentLevel(requiredLevel);
		workflow.setStatus(decision);
		approvalWorkFlowRepository.save(workflow);
		publishApprovalEventIfNeeded(prId, decision, requiredLevel, approvalDecisionDto.getReason(), approverId);

		return savedDecision;
	}

	private void publishApprovalEventIfNeeded(UUID prId, Decision decision, ApprovalLevel decidedLevel, String reason,
			UUID approverId) {
		boolean shouldPublish = decision == Decision.REJECTED || isFinalApproval(prId);
		if (!shouldPublish) {
			return;
		}
		ApprovalWorkflow workflow = approvalWorkFlowRepository.findById(prId)
				.orElseThrow(() -> new RuntimeException("Purchase request not found for id: " + prId));
		workflow.setCurrentLevel(decidedLevel);
		workflow.setStatus(decision);
		approvalWorkFlowRepository.save(workflow);
		PurchaseRequestDecisionEvent event = new PurchaseRequestDecisionEvent();
		event.setPrId(prId);
		event.setApprovalLevel(decidedLevel);
		event.setDecision(decision);
		event.setReason(reason);
		event.setTotalEstimate(workflow.getTotalEstimate());
		event.setEventType(handleEventDecision(decision));
		kafkaPurchaseApprovalProducer.handlePurchaseRequestApproval(event);
	}

	private boolean isFinalApproval(UUID prId) {
		for (ApprovalLevel level : APPROVAL_FLOW) {
			boolean approved = repository.existsByPrIdAndDecisionAndLevel(prId, Decision.APPROVED, level);
			if (!approved) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<ApprovalDecisions> findAllPendingDecisions(Authentication authentication) {

		Set<String> permissions = authorities(authentication);
		if (!has(permissions, "APPROVAL_PENDING_LIST")) {

			throw new AccessDeniedException("You don't have permission to view pending approvals");
		}
		return repository.findAllByDecision(Decision.PENDING_APPROVAL);

	}

	@Override
	public List<ApprovalDecisions> getApprovalHistory(UUID prId) {
		List<ApprovalDecisions> listAllPR = repository.findAllByPrId(prId);
		if (listAllPR.isEmpty()) {
			throw new PurchaseRequestNotFoundException("No approval history : " + prId);
		}

		return listAllPR;
	}

	private EventType handleEventDecision(Decision decision) {
		return switch (decision) {
		case APPROVED -> EventType.APPROVED;
		case REJECTED -> EventType.REJECTED;
		default -> {

			throw new IllegalArgumentException("Only APPROVED or REJECTED decisions are allowed " + decision);
		}
		};
	}

	private ApprovalLevel getRequiredApprovalLevel(UUID prId) {
		for (ApprovalLevel level : APPROVAL_FLOW) {
			boolean approved = repository.existsByPrIdAndDecisionAndLevel(prId, Decision.APPROVED, level);

			boolean rejected = repository.existsByPrIdAndDecisionAndLevel(prId, Decision.REJECTED, level);
			if (rejected) {

				throw new RuntimeException("Purchase request was rejected by " + level);
			}
			if (!approved) {
				return level;
			}
		}

		throw new RuntimeException("All approval decisions have already been recorded");
	}
}
