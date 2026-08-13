package com.vinod.b2b.serviceimpl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.vinod.b2b.entity.Supplier;
import com.vinod.b2b.service.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService{

	@Override
	public Supplier createSupplier(Supplier supplier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Supplier updateSupplierStatus(UUID supplierId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Supplier> getAllSuppliers(int page, String sortBy) {
		// TODO Auto-generated method stub
		return null;
	}

}
