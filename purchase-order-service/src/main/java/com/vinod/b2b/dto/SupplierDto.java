package com.vinod.b2b.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDto {
	private UUID supplierId;
	private String supplierName;
	private String category;
	private Boolean isActive;
}
