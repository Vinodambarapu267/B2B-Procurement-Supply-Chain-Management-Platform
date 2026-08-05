package com.example.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Users;

public interface UserCredentialRepository extends JpaRepository<Users, UUID> {
	Optional<Users> findByUsername(String username);

	Optional<Users> findByEmail(String email);
}
