package com.vinod.b2b.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinod.b2b.dto.ApprovalDecisionDto;
import com.vinod.b2b.entity.ApprovalDecisions;
import com.vinod.b2b.service.ApprovalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/appprovals")
@RequiredArgsConstructor
public class ApprovalController {
	private final ApprovalService approvalService;

	@PostMapping("/{prid}/decision")
	public ApprovalDecisions makeDecision(@PathVariable(name = "prid") UUID prId,
			@RequestBody ApprovalDecisionDto decisionDto) {
		return approvalService.addApporvalDecision(prId, decisionDto);
	}

}
