package com.vinod.b2b.dto;

import java.util.UUID;

import com.vinod.b2b.utility.ApprovalLevel;
import com.vinod.b2b.utility.Decision;

import lombok.Data;
@Data
public class ApprovalDecisionDto {
	private UUID approverId;
	private ApprovalLevel level;
	private Decision decision;
	private String reason;
}
