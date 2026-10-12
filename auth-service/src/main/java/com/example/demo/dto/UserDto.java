package com.example.demo.dto;

import java.util.UUID;

import com.example.demo.utility.Roles;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
	private UUID userId;
	private String username;
	@Enumerated(EnumType.STRING)
	private Roles role;
	private Boolean status;
}
