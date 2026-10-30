package com.vinod.b2b.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinod.b2b.entity.PurchaseOrders;
import com.vinod.b2b.utility.OrderStatus;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrders, UUID> {
	Optional<PurchaseOrders> findByPrIdAndStatus(UUID prId, OrderStatus status);
}
