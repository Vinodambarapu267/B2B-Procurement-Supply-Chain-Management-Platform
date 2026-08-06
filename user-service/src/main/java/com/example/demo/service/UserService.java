package com.example.demo.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.dto.UserProfileDto;
import com.example.demo.entity.UserProfiles;

@Service
public interface UserService {
	UserProfiles createUser(UserProfiles user);

	UserProfiles updateUser(UUID id, UserProfileDto profileDto);

	UserProfiles updateUserRole(Long id);

	String updateStatus(Long id);

	String deleteUser(String name);
}
