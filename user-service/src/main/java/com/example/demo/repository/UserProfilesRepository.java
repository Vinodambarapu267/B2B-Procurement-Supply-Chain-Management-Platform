package com.example.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserProfiles;
import com.example.demo.repository.utility.Roles;

public interface UserProfilesRepository extends JpaRepository<UserProfiles, UUID> {
	Optional<UserProfiles> findByName(String name, Roles role);
}
