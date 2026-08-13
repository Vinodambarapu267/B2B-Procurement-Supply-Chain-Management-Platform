package com.vinod.b2b.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinod.b2b.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
	Optional<Supplier> findBySupplierName(String supplierName);
}
