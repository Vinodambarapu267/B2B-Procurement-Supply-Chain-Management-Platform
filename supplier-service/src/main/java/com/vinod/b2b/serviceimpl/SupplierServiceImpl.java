package com.vinod.b2b.serviceimpl;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vinod.b2b.dto.SupplierDto;
import com.vinod.b2b.dto.UserDto;
import com.vinod.b2b.entity.Supplier;
import com.vinod.b2b.exceptions.SupplierAlreadyExistException;
import com.vinod.b2b.exceptions.SupplierNotFoundException;
import com.vinod.b2b.feignclients.UserServiceFeignClient;
import com.vinod.b2b.repository.SupplierRepository;
import com.vinod.b2b.service.SupplierService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
	private final SupplierRepository repository;
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
	@Transactional
	public Supplier updateSupplier(UUID supplierId, SupplierDto supplierDto) {
		Supplier supplier = repository.findById(supplierId)
				.orElseThrow(() -> new SupplierNotFoundException("Supplier not found ID : " + supplierId));
		supplier.setSupplierName(supplierDto.getSupplierName());
		supplier.setCategory(supplierDto.getCategory());
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
