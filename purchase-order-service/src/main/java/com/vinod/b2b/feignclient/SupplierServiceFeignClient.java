package com.vinod.b2b.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vinod.b2b.dto.SupplierDto;

@FeignClient(name = "SUPPLIER-SERVICE", url = "${supplier.service.base.url}")
public interface SupplierServiceFeignClient {
	@GetMapping("/{name}")
	public SupplierDto getSupplierByName(@PathVariable String name);
}
