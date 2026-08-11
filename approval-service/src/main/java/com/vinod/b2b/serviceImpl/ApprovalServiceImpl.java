package com.vinod.b2b.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.vinod.b2b.entity.ApprovalDecisions;
import com.vinod.b2b.service.ApprovalService;
import com.vinod.b2b.utility.Decision;

@Service
public class ApprovalServiceImpl implements ApprovalService{

	@Override
	public ApprovalDecisions addApporvalDecision(UUID prId, UUID approvalId, Decision decision) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ApprovalDecisions> findAllPendingDecisions(UUID approvalId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ApprovalDecisions> getApprovalHistory(UUID prId) {
		// TODO Auto-generated method stub
		return null;
	}

}
