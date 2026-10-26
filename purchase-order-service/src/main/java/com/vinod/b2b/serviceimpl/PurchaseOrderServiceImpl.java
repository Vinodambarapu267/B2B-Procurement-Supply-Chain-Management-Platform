package com.vinod.b2b.serviceimpl;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.vinod.b2b.dto.UserDto;
import com.vinod.b2b.entity.PurchaseOrders;
import com.vinod.b2b.exceptions.PurchaseOrderNotFoundException;
import com.vinod.b2b.feignclient.UserServiceFeignClient;
import com.vinod.b2b.repository.PurchaseOrderRepository;
import com.vinod.b2b.service.PurchaseOrderService;
import com.vinod.b2b.utility.OrderStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
	private final PurchaseOrderRepository repository;
	private final UserServiceFeignClient feignClient;

	private Set<String> authorities(Authentication authentication) {
		if (authentication == null)
			return Set.of();
		return authentication.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toSet());
	}

	private boolean has(Set<String> authorities, String permission) {
		return authorities.contains(permission);
	}

	private void requirePermission(Set<String> authorities, String permission) {
		if (!has(authorities, permission)) {
			throw new AccessDeniedException("Missing permission: " + permission);
		}
	}

	private UUID resolveUserId(String username) {
		UserDto user = feignClient.findByName(username);
		if (user == null || user.getUserId() == null) {
			throw new RuntimeException("User Not found by username: " + username);
		}
		return user.getUserId();
	}

	@Override
	public PurchaseOrders generatePurchaseOrder(PurchaseOrders purchaseOrders, Authentication authentication) {
		Set<String> permissions = authorities(authentication);
		if (!has(permissions, "PURCHASE_ORDER_CREATE")) {
			throw new AccessDeniedException("You don't have Pemission to create purchase orders");
		}
		PurchaseOrders order = new PurchaseOrders();
		order.setPrId(purchaseOrders.getPrId());
		order.setSupplierId(purchaseOrders.getSupplierId());
		order.setPoNumber(generateRandomPONumber());
		order.setStatus(OrderStatus.GENERATED);
		order.setTotalAmount(purchaseOrders.getTotalAmount());
		return repository.save(order);
	}

	@Override
	public PurchaseOrders updatePurchaseOrderstatus(UUID purchaseOrderId, OrderStatus status) {
		PurchaseOrders purchaseOrder = repository.findById(purchaseOrderId).orElseThrow(
				() -> new PurchaseOrderNotFoundException("Purchase Order not found ID : " + purchaseOrderId));
		purchaseOrder.setStatus(status);
		purchaseOrder.setVersion(purchaseOrder.getVersion() + 1);
		return repository.save(purchaseOrder);
	}

	@Override
	public PurchaseOrders getPurchaseOrder(UUID purchaseOrderId, Authentication authentication) {
		UUID supplierId = resolveUserId(authentication.getName());
		Set<String> permissions = authorities(authentication);
		PurchaseOrders order = repository.findById(purchaseOrderId).orElseThrow(
				() -> new PurchaseOrderNotFoundException("PurchaseOrder not found ID : " + purchaseOrderId));

		if (permissions.contains("PURCHASE_ORDER_READ")) {
			return order;

		}
		if (permissions.contains("PURCHASE_ORDER_READ_ASSIGNED")) {
			if (order.getSupplierId().equals(supplierId)) {
				return order;
			}
		}
		throw new AccessDeniedException("You don't have pemission to view this purchase order");

	}

	@Override
	public PurchaseOrders sendPurchaseOrder(UUID purchaseOrderId) {
		PurchaseOrders order = repository.findById(purchaseOrderId).orElseThrow(
				() -> new PurchaseOrderNotFoundException("PurchaseOrder not found ID : " + purchaseOrderId));

		if (order.getStatus() != OrderStatus.GENERATED) {
			throw new IllegalStateException(
					"Only generated purchase orders can be sent. Current status: " + order.getStatus());
		}
		order.setStatus(OrderStatus.SENT);
		return repository.save(order);
	}

	@Override
	public PurchaseOrders acceptPurchaseOrder(UUID purchaseOrderId, Authentication authentication) {
		UUID supplierId = resolveUserId(authentication.getName());
		Set<String> permissions = authorities(authentication);
		if (!has(permissions, "PURCHASE_ORDER_SUPPLIER_ACTION")) {
			throw new AccessDeniedException("You don't have to send this purchase order");
		}
		PurchaseOrders order = repository.findById(purchaseOrderId).orElseThrow(
				() -> new PurchaseOrderNotFoundException("PurchaseOrder not found ID : " + purchaseOrderId));
		if (!order.getSupplierId().equals(supplierId)) {
			throw new AccessDeniedException("You cannot send another supplier's purchase order");
		}
		if (order.getStatus().equals(OrderStatus.ACCEPTED)) {
			throw new IllegalArgumentException(
					"You Already Accepted This purchase Order : " + order.getPurchaseOrderId());
		}
		if (order.getStatus().equals(OrderStatus.SENT)) {
			order.setStatus(OrderStatus.ACCEPTED);
		} else {
			throw new IllegalStateException(
					"Only SENT purchase orders can be accepted. Current status: " + order.getStatus());
		}
		return repository.save(order);
	}

	@Override
	public PurchaseOrders dispatchPurchaseOrder(UUID purchaseOrderId, Authentication authentication) {
		UUID supplierId = resolveUserId(authentication.getName());
		Set<String> permissions = authorities(authentication);
		if (!has(permissions, "PURCHASE_ORDER_SUPPLIER_ACTION")) {
			throw new AccessDeniedException("You don't have to dispatch this purchase order");
		}
		PurchaseOrders order = repository.findById(purchaseOrderId).orElseThrow(
				() -> new PurchaseOrderNotFoundException("PurchaseOrder not found ID : " + purchaseOrderId));
		if (!order.getSupplierId().equals(supplierId)) {
			throw new AccessDeniedException("You cannot send another supplier's purchase order");
		}
		if (order.getStatus().equals(OrderStatus.DISPATCHED)) {
			throw new IllegalArgumentException(
					"You Already Dispacted This purchase Order : " + order.getPurchaseOrderId());
		}
		if (order.getStatus().equals(OrderStatus.ACCEPTED)) {
			order.setStatus(OrderStatus.DISPATCHED);
		} else {
			throw new IllegalStateException(
					"Only ACCEPTED purchase orders can be dispatched. Current status: " + order.getStatus());
		}
		return repository.save(order);

	}

	@Override
	public PurchaseOrders completePurchaseOrder(UUID purchaseOrderId) {

		PurchaseOrders order = repository.findById(purchaseOrderId).orElseThrow(
				() -> new PurchaseOrderNotFoundException("PurchaseOrder not found ID : " + purchaseOrderId));

		if (order.getStatus() != OrderStatus.DISPATCHED) {
			throw new IllegalStateException(
					"Only Accepted purchase orders can be sent. Current status: " + order.getStatus());
		}
		order.setStatus(OrderStatus.COMPLETED);
		return repository.save(order);
	}

	private String generateRandomPONumber() {
		return UUID.randomUUID().toString().toUpperCase().substring(0, 8);
	}

}
