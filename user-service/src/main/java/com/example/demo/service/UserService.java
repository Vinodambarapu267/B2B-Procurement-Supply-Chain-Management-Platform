package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.UserProfileDto;
import com.example.demo.entity.UserProfiles;

@Service
public interface UserService {
	UserProfiles createUser(UserProfiles user);

	Optional<UserProfiles> updateUser(UserProfileDto profileDto);

	Optional<UserProfiles> updateUserRole(Long id);

	String updateStatus(Long id);

	String deleteUser(String name);
}
