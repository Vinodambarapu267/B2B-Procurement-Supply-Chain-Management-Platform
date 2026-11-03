package com.example.demo.kafka;

import java.util.List;
import java.util.UUID;

import com.example.demo.dto.PurchaseItem;
import com.example.demo.utility.PurchaseStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderEvent {
	private UUID purchaseOrderId;
	private UUID prId;
	private List<PurchaseItem> items;
	private EventType eventType;
	private PurchaseStatus status;

}