package com.example.demo.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthRequest;
import com.example.demo.entity.Users;
import com.example.demo.repository.UserCredentialRepository;
import com.example.demo.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	private final AuthenticationManager authenticationManager;
	private final UserCredentialRepository credentialRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public Users registerUser(@RequestBody Users user) {
		return authService.registerUser(user);
	}

	@PostMapping("/token")
	public String getToken(@RequestBody AuthRequest authRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		Users user = credentialRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new RuntimeException("User not Found"));
		String token = authService.generateToken(user.getUsername(), user.getRole());
		System.out.println("Matches     : " + passwordEncoder.matches(authRequest.getPassword(), user.getPassword()));
		  if (authentication.isAuthenticated()) {
		        return authService.generateToken(authRequest.getUsername(),user.getRole());
		    } else {
		        throw new RuntimeException("invalid access");
		    }
	}
}
