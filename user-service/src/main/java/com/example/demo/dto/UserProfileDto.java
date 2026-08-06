package com.example.demo.dto;

import java.util.UUID;

import com.example.demo.repository.utility.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
	@Column(nullable = false, length = 150)
	private String name;
	@Column(nullable = false, length = 100)
	private String department;
	@Column(length = 150)
	private String jobTitle;
	@Enumerated(EnumType.STRING)
	private Roles role;
	
	private UUID manager;
	private Boolean isActive;

}
