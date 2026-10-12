package com.example.demo.dto;

import java.util.UUID;

import com.example.demo.utility.Roles;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
	private UUID userId;
	private String username;
	private Roles role;
	private boolean isActive;
}
