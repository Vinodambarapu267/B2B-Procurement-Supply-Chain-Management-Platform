package com.vinod.b2b.dto;

import lombok.Data;

@Data
public class PurchaseItem {
	private String itemName;
	private Integer quantity;
	private Double unitPrice;

}
