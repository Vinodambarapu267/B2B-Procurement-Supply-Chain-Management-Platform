package com.example.demo.serviceimpl;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.PurchaseItem;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.PurchaseRequest;
import com.example.demo.exception.PurchaseRequestNotFoundException;
import com.example.demo.exception.RequesterNotFoundException;
import com.example.demo.feignclient.UserServiceFeignClient;
import com.example.demo.kafka.EventType;
import com.example.demo.kafka.KafkaPurchaseRequestProducer;
import com.example.demo.kafka.PurchaseRequestEvent;
import com.example.demo.repository.PurchaseRequestRepository;
import com.example.demo.service.ProcurementService;
import com.example.demo.utility.PurchaseStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcurmentServiceImpl implements ProcurementService {
	private final PurchaseRequestRepository repository;
	private final KafkaPurchaseRequestProducer kafkaPurchaseRequestProducer;
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
	public PurchaseRequest createPurchaseRequest(PurchaseRequest purchaseRequest, Authentication authentication) {
		UUID requesterId = resolveUserId(authentication.getName());
		PurchaseRequest newPurchaseRequest = new PurchaseRequest();
		newPurchaseRequest.setRequesterId(requesterId);
		newPurchaseRequest.setStatus(purchaseRequest.getStatus());
		Double totalEstimation = 0.0;
		List<PurchaseItem> items = purchaseRequest.getItems();
		if (items == null || items.isEmpty()) {
			throw new IllegalArgumentException("Purchase request must contain atleast one item");
		}
		for (PurchaseItem item : items) {
			totalEstimation += item.getUnitPrice() * item.getQuantity();
		}
		newPurchaseRequest.setItems(items);
		newPurchaseRequest.setTotalEstimation(totalEstimation);
		PurchaseRequest save = repository.save(newPurchaseRequest);

		// Event created for kafka Producer
		PurchaseRequestEvent event = new PurchaseRequestEvent();
		event.setPrId(save.getPurchaseId());
		event.setRequesterId(save.getRequesterId());
		event.setTotalEstimation(save.getTotalEstimation());
		event.setVersion(save.getVersion());
		event.setStatus(save.getStatus());
		event.setEventType(EventType.CREATED);
		kafkaPurchaseRequestProducer.publisPurchaseRequestCreated(event);
		return save;
	}

	@Override
	public PurchaseRequest findByPurchaseRequestId(UUID purchaseId, Authentication authentication) {
		PurchaseRequest request = repository.findById(purchaseId)
				.orElseThrow(() -> new PurchaseRequestNotFoundException("This PR Not found"));
		Set<String> permissions = authorities(authentication);

		if (has(permissions, "PURCHASE_REQUEST_READ")) {
			return request;
		}
		UUID userId = resolveUserId(authentication.getName());
		if (userId.equals(request.getRequesterId()) && has(permissions, "PURCHASE_REQUEST_READ_OWN")) {
			return request;
		}

		throw new AccessDeniedException("You do not have permission to view this purchase request");
	}

	@Override
	public List<PurchaseRequest> findAllPurchaseRequest(Authentication authentication) {
		UUID requesterId = resolveUserId(authentication.getName());
		List<PurchaseRequest> list = repository.findByrequesterId(requesterId);
		if (list.isEmpty()) {
			throw new RequesterNotFoundException("No Purchase Request Found");
		}
		return list;
	}

	@Override
	@Transactional
	@Modifying
	public String cancelPurchaseRequest(UUID purchaseId, Authentication authentication) {
		PurchaseRequest purchaseRequest = repository.findById(purchaseId)
				.orElseThrow(() -> new PurchaseRequestNotFoundException("This PR Not found"));

		UUID requesterId = resolveUserId(authentication.getName());
		Set<String> permissions = authorities(authentication);
		boolean isOwner = requesterId.equals(purchaseRequest.getRequesterId());
		boolean isAdmin = has(permissions, "PURCHASE_REQUEST_CANCEL_ANY");
		if (!isOwner && !isAdmin) {
			throw new AccessDeniedException("You don't have permission to cancel this purchase request");
		}
		PurchaseStatus status = purchaseRequest.getStatus();
	
		// cannnot cancel in terminal state
		if (status == PurchaseStatus.APPROVED || status == PurchaseStatus.COMPLETED
				|| status == PurchaseStatus.PO_GENERATED) {

			throw new IllegalStateException("You cannot cancel a purchase request in this status");
		}

		// if status is already cancelled
		if (status == PurchaseStatus.CANCELLED) {
			throw new IllegalStateException("Purchase request is already cancelled");
		}

		purchaseRequest.setStatus(PurchaseStatus.CANCELLED);
		return "purchase requeest cancelled successfully";
	}

}
