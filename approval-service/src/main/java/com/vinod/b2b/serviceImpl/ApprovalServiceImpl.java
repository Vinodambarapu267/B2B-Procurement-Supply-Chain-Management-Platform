package com.vinod.b2b.serviceImpl;


import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.vinod.b2b.dto.ApprovalDecisionDto;
import com.vinod.b2b.dto.UserDto;
import com.vinod.b2b.entity.ApprovalDecisions;
import com.vinod.b2b.exceptions.ApproverNotFoundException;
import com.vinod.b2b.exceptions.PurchaseRequestNotFoundException;
import com.vinod.b2b.feignclients.UserServiceFeignClient;
import com.vinod.b2b.repository.ApprovalRepository;
import com.vinod.b2b.service.ApprovalService;

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

	private UUID resolveUserId(String username) {
		UserDto user = feignClient.findByName(username);
		if (user == null || user.getUserId() == null) {
			throw new RuntimeException("User Not found by username: " + username);
		}
		return user.getUserId();
	}

	@Override
	public ApprovalDecisions addApporvalDecision(UUID prId, ApprovalDecisionDto approvalDecisionDto) {
		ApprovalDecisions approvaDecisions = new ApprovalDecisions();
		approvaDecisions.setPrId(prId);
		approvaDecisions.setApproverId(approvalDecisionDto.getApproverId());
		approvaDecisions.setDecision(approvalDecisionDto.getDecision());
		approvaDecisions.setLevel(approvalDecisionDto.getLevel());
		approvaDecisions.setReason(approvalDecisionDto.getReason());
		return repository.save(approvaDecisions);

	}

	@Override
	public List<ApprovalDecisions> findAllPendingDecisions(UUID approvalId) {
		List<ApprovalDecisions> allPendingPRs = repository.findAllByApproverId(approvalId);
		if (allPendingPRs.isEmpty()) {
			throw new ApproverNotFoundException("NO pending records");
		}
		return allPendingPRs;
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
