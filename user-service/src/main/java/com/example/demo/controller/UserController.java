package com.example.demo.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserProfileDto;
import com.example.demo.entity.UserProfiles;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/createuser")
	public UserProfiles createNewUser(@RequestBody UserProfiles user) {
		return userService.createUser(user);
	}

	@PutMapping("/updateuser/{id}")
	public UserProfiles updateUser(@PathVariable UUID id, @RequestBody UserProfileDto profileDto) {
		return userService.updateUser(id, profileDto);
	}

	@PatchMapping("/{id}/role")
	public UserProfiles updateUserRole(@PathVariable UUID id, @RequestParam String role) {
		return userService.updateUserRole(id, role);
	}

	@PatchMapping("/{id}/status")
	public String updateStatus(@PathVariable UUID id) {
		return userService.updateStatus(id);
	}
}
