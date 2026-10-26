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
import com.vinod.b2b.exceptions.ApproverNotFoundException;
import com.vinod.b2b.exceptions.PurchaseRequestNotFoundException;
import com.vinod.b2b.feignclients.UserServiceFeignClient;
import com.vinod.b2b.repository.ApprovalRepository;
import com.vinod.b2b.service.ApprovalService;
import com.vinod.b2b.utility.ApprovalLevel;
import com.vinod.b2b.utility.Decision;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {
	private final ApprovalRepository repository;
	private final UserServiceFeignClient feignClient;

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
		Set<String> permission = authorities(authentication);
		if (!has(permission, "APPROVAL_DECIDE")) {
			throw new AccessDeniedException("You Don't permission to make approve");
		}

		ApprovalLevel currentRole = getCurrentRole(authentication);

		UUID approverId = resolveUserId(authentication.getName());
		ApprovalDecisions approvaDecisions = new ApprovalDecisions();
		approvaDecisions.setPrId(prId);
		approvaDecisions.setApproverId(approverId);
		approvaDecisions.setDecision(approvalDecisionDto.getDecision());
		approvaDecisions.setLevel(currentRole);
		approvaDecisions.setReason(approvalDecisionDto.getReason());
		return repository.save(approvaDecisions);

	}

	@Override
	public List<ApprovalDecisions> findAllPendingDecisions(Authentication authentication) {

		Set<String> permissions = authorities(authentication);
		if (!has(permissions, "APPROVAL_PENDING_LIST")) {
			throw new AccessDeniedException("You don't have permission to view pending approvals");
		}
		UUID approverId = resolveUserId(authentication.getName());
		return repository.findAllByApproverIdAndDecision(approverId, Decision.PENDING);

	}

	@Override
	public List<ApprovalDecisions> getApprovalHistory(UUID prId) {
		List<ApprovalDecisions> listAllPR = repository.findAllByPrId(prId);
		if (listAllPR.isEmpty()) {
			throw new PurchaseRequestNotFoundException("No approval history : " + prId);
		}

		return listAllPR;
	}

}
