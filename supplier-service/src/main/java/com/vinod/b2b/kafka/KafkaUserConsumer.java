package com.vinod.b2b.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.vinod.b2b.entity.Supplier;
import com.vinod.b2b.repository.SupplierRepository;

@Service
public class KafkaUserConsumer {
	private final SupplierRepository supplierRepository;

	public KafkaUserConsumer(SupplierRepository supplierRepository) {
		super();
		this.supplierRepository = supplierRepository;
	}

	@KafkaListener(topics = { "user-registered", "user-status-changed" }, groupId = "supplier-service-group")
	public void handleUserConsumerEvent(UserEvent event) {
		System.err.println("supplier event" + event);
		if (event == null || event.getEventType() == null) {
			throw new RuntimeException("UserEvent or Event type is null");
		}
		switch (event.getEventType()) {
		case REGISTERED -> handleUserCreated(event);

		}
	}

	public void handleUserCreated(UserEvent event) {
		  if (supplierRepository.existsById(event.getUserId())) {
		        return; 
		    }

		supplierRepository.findBySupplierName(event.getUsername()).ifPresent(supplier -> {
			throw new RuntimeException("User already exists with name");
		});

		Supplier supplier = new Supplier();
		supplier.setSupplierId(event.getUserId());
		supplier.setSupplierName(event.getUsername());
		supplier.setIsActive(event.getIsActive());
		supplier.setCategory("update the category");
		supplierRepository.save(supplier);
	}
}
