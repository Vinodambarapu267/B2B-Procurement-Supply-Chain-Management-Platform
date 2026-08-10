package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.PurchaseRequest;

public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequest, UUID> {
	List<PurchaseRequest> findByrequesterId(UUID rrequesterId);
}
