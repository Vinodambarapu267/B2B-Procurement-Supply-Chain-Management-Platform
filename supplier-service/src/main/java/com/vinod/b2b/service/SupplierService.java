package com.vinod.b2b.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.vinod.b2b.entity.Supplier;

@Service
public interface SupplierService {
	Supplier createSupplier(Supplier supplier);

	Supplier updateSupplierStatus(UUID supplierId);

	Page<Supplier> getAllSuppliers(int page, String sortBy);
}
