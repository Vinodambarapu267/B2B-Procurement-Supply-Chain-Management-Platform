package com.vinod.b2b.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.vinod.b2b.dto.ApprovalDecisionDto;
import com.vinod.b2b.entity.ApprovalDecisions;
import com.vinod.b2b.repository.ApprovalRepository;
import com.vinod.b2b.service.ApprovalService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {
	private final ApprovalRepository repository;

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
			throw new RuntimeException("NO pending records");
		}
		return allPendingPRs;
	}

	@Override
	public List<ApprovalDecisions> getApprovalHistory(UUID prId) {
		List<ApprovalDecisions> listAllPR = repository.findAllByPrId(prId);
		if (listAllPR.isEmpty()) {
			throw new RuntimeException("No approval history : " + prId);
		}

		return listAllPR;
	}

}
