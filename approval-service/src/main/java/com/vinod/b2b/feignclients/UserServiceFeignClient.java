package com.vinod.b2b.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vinod.b2b.dto.UserDto;

@FeignClient(name = "USER-SERVICE",url = "http://localhost:8082/api/v1/user")
public interface UserServiceFeignClient {
	@GetMapping("/{name}")
	UserDto findByName(@PathVariable String name);
}
