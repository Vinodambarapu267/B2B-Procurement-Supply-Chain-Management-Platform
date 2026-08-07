package com.example.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.UserProfiles;
import com.example.demo.utility.Roles;

public interface UserProfilesRepository extends JpaRepository<UserProfiles, UUID> {
	Optional<UserProfiles> findByNameAndRole(String name, Roles role);

	Optional<UserProfiles> findByName(String name);
	@Modifying
	@Query("""
	    UPDATE UserProfiles u
	    SET u.manager = null
	    WHERE u.manager.id = :managerId
	    """)
	void removeManagerReference(@Param("managerId") UUID managerId);
}
