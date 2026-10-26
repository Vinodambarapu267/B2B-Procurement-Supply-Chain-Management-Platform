package com.vinod.b2b.controller;

import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vinod.b2b.dto.ApprovalDecisionDto;
import com.vinod.b2b.entity.ApprovalDecisions;
import com.vinod.b2b.service.ApprovalService;
import com.vinod.b2b.utility.ResponseMessage;
import com.vinod.b2b.utility.ResponseStatus;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/appprovals")
@RequiredArgsConstructor
public class ApprovalController {
	private final ApprovalService approvalService;

	@PostMapping("/{prid}/decision")
	@PreAuthorize("hasAuthority('APPROVAL_DECIDE')")
	public ResponseEntity<?> makeDecision(@PathVariable(name = "prid") UUID prId,
			@RequestBody ApprovalDecisionDto decisionDto, Authentication authentication) {
		ApprovalDecisions apporvalDecision = approvalService.addApporvalDecision(prId, decisionDto, authentication);
		System.out.println(authentication);
		if (apporvalDecision == null) {
			return ResponseEntity.ok(ResponseMessage.builder().message("approval Decision added failed")
					.status(ResponseStatus.FAILED.name()).statusCode(HttpURLConnection.HTTP_NOT_ACCEPTABLE).build());
		}
		return ResponseEntity.ok(ResponseMessage.builder().message("approval Decision added successfully")
				.status(ResponseStatus.SUCCESS.name()).statusCode(HttpURLConnection.HTTP_CREATED).data(apporvalDecision)
				.build());
	}

	@GetMapping("/pending")
	@PreAuthorize("hasAuthority('APPROVAL_PENDING_LIST')")
	public ResponseEntity<?> getAllPendingPRs(Authentication authentication) {
		List<ApprovalDecisions> allPendingDecisions = approvalService.findAllPendingDecisions(authentication);
		if (allPendingDecisions == null) {
			return ResponseEntity.ok(ResponseMessage.builder().message("Retriving pending Records failed")
					.status(ResponseStatus.FAILED.name()).statusCode(HttpURLConnection.HTTP_NOT_FOUND).build());
		}
		return ResponseEntity.ok(ResponseMessage.builder().message("Pending record rretrived successfully")
				.status(ResponseStatus.SUCCESS.name()).statusCode(HttpURLConnection.HTTP_NOT_ACCEPTABLE)
				.data(allPendingDecisions).build());
	}

	@GetMapping("/{prid}/history")
	public ResponseEntity<?> getHistoryOfPr(@PathVariable(name = "prid") UUID prId) {
		List<ApprovalDecisions> approvalHistory = approvalService.getApprovalHistory(prId);
		if (approvalHistory == null) {
			return ResponseEntity.ok(ResponseMessage.builder().message("Retriving history failed")
					.status(ResponseStatus.FAILED.name()).statusCode(HttpURLConnection.HTTP_NOT_ACCEPTABLE).build());
		}
		return ResponseEntity.ok(ResponseMessage.builder().message("PR history retrived successfully")
				.status(ResponseStatus.SUCCESS.name()).statusCode(HttpURLConnection.HTTP_CREATED).data(approvalHistory)
				.build());
	}
}
