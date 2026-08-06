package com.example.demo.dto;

import java.util.UUID;

import com.example.demo.entity.UserProfiles;
import com.example.demo.repository.utility.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_id")
	private UserProfiles manager;
	private Boolean isActive;

}
