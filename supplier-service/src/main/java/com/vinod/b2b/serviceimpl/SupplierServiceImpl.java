package com.vinod.b2b.serviceimpl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.vinod.b2b.entity.Supplier;
import com.vinod.b2b.exceptions.SupplierAlreadyExistException;
import com.vinod.b2b.exceptions.SupplierNotFoundException;
import com.vinod.b2b.repository.SupplierRepository;
import com.vinod.b2b.service.SupplierService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
	private final SupplierRepository repository;

	@Override
	public Supplier createSupplier(Supplier supplier) {
		repository.findBySupplierName(supplier.getSupplierName()).ifPresent(newPerson -> {
			throw new SupplierAlreadyExistException(
					"This Supplier ALready Present Name : " + supplier.getSupplierName());
		});
		Supplier newSupplier = new Supplier();
		newSupplier.setSupplierName(supplier.getSupplierName());
		newSupplier.setCategory(supplier.getCategory());
		return repository.save(newSupplier);
	}

	@Override
	public Supplier updateSupplierStatus(UUID supplierId) {
		Supplier supplier = repository.findById(supplierId)
				.orElseThrow(() -> new SupplierNotFoundException("Supplier not Found ID : " + supplierId));
		Boolean isActive = supplier.getIsActive();
		supplier.setIsActive(!isActive);
		return repository.save(supplier);

	}

	@Override
	public Page<Supplier> getAllSuppliers(int page, String sortBy) {
		PageRequest pageable = PageRequest.of(page, 10, Sort.by(sortBy).descending());
		Page<Supplier> all = repository.findAll(pageable);
		if (all.isEmpty()) {
			throw new SupplierNotFoundException("NO Records.. ");
		}
		return all;
	}

}
