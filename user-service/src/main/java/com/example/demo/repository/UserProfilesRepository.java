package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserProfiles;

public interface UserProfilesRepository extends JpaRepository<UserProfiles, UUID> {

}
