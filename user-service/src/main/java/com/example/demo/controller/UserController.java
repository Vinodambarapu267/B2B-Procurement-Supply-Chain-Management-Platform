package com.example.demo.controller;

import java.net.HttpURLConnection;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.example.demo.utility.ResponseMessage;
import com.example.demo.utility.ResponseStatus;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/createuser")
	public ResponseEntity<?> createNewUser(@RequestBody UserProfiles user) {
		UserProfiles userProfile = userService.createUser(user);
		if (userProfile != null) {
			return ResponseEntity.ok(new ResponseMessage<>(HttpURLConnection.HTTP_CREATED,
					ResponseStatus.SUCCESS.name(), "userProfile created Successfully", userProfile));
		}
		return ResponseEntity.ok(new ResponseMessage<>(HttpURLConnection.HTTP_NOT_FOUND, ResponseStatus.FAILURE.name(),
				"userProfile creating failed"));
	}

	@PutMapping("/updateuser/{id}")
	public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody UserProfileDto profileDto) {
		UserProfiles userProfile = userService.updateUser(id, profileDto);
		if (userProfile != null) {
			return ResponseEntity.ok(new ResponseMessage<>(HttpURLConnection.HTTP_ACCEPTED,
					ResponseStatus.SUCCESS.name(), "userProfile updated Successfully", userProfile));
		}
		return ResponseEntity.ok(new ResponseMessage<>(HttpURLConnection.HTTP_NOT_MODIFIED,
				ResponseStatus.FAILURE.name(), "userProfile updated failed"));
	}

	@PatchMapping("/{id}/role")
	public ResponseEntity<?> updateUserRole(@PathVariable UUID id, @RequestParam String role) {
		UserProfiles updateUserRole = userService.updateUserRole(id, role);
		if (updateUserRole != null) {
			return ResponseEntity.ok(new ResponseMessage<>(HttpURLConnection.HTTP_ACCEPTED,
					ResponseStatus.SUCCESS.name(), "userRole updated Successfully", updateUserRole));
		}
		return ResponseEntity.ok(new ResponseMessage<>(HttpURLConnection.HTTP_NOT_MODIFIED,
				ResponseStatus.FAILURE.name(), "User Role update failed"));
	}

	@PatchMapping("/{id}/status")
	public String updateStatus(@PathVariable UUID id) {
		return userService.updateStatus(id);
	}

	@DeleteMapping("/deleteUser/{name}")
	public String deleteUser(@PathVariable String name) {
		return userService.deleteUser(name);
	}

	@GetMapping("/{name}")
	public ResponseEntity<?> findByName(@PathVariable String name) {
		UserProfiles userProfile = userService.findByName(name);
		if (userProfile != null) {
			return ResponseEntity.ok(new ResponseMessage<>(HttpURLConnection.HTTP_OK, ResponseStatus.SUCCESS.name(),
					"userRole updated Successfully", userProfile));
		}
		return ResponseEntity.ok(new ResponseMessage<>(HttpURLConnection.HTTP_NOT_FOUND, ResponseStatus.FAILURE.name(),
				"User Role update failed"));
	}

}
