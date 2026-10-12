package com.example.demo.serviceimpl;

import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.UserProfileDto;
import com.example.demo.entity.UserProfiles;
import com.example.demo.exceptions.ManagerNotFoundException;
import com.example.demo.exceptions.UserAlreadyExistException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repository.UserProfilesRepository;
import com.example.demo.service.UserService;
import com.example.demo.utility.Roles;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceimpl implements UserService {
	private final UserProfilesRepository repository;

	@Override
	public UserProfiles createUser(UserProfiles user) {
		repository.findByNameAndRole(user.getName(), user.getRole()).ifPresent(users -> {
			throw new UserAlreadyExistException("User Already Exist with Name :" + user.getName());
		});
		UserProfiles newUser = new UserProfiles();
		newUser.setName(user.getName());
		newUser.setDepartment(user.getDepartment());
		newUser.setJobTitle(user.getJobTitle());
		newUser.setIsActive(user.getIsActive());
		newUser.setManager(user.getManager());
		newUser.setRole(user.getRole());
		return repository.save(newUser);
	}

	@Override
	@Transactional
	public UserProfiles updateUser(UUID id, UserProfileDto profileDto) {
		UserProfiles existedUser = repository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not Found" + id));
		existedUser.setName(profileDto.getName());
		existedUser.setDepartment(profileDto.getDepartment());
		existedUser.setJobTitle(profileDto.getJobTitle());
		existedUser.setRole(profileDto.getRole());
		if (profileDto.getManager() != null) {
			UserProfiles mananger = repository.findById(profileDto.getManager()).orElseThrow(
					() -> new ManagerNotFoundException("Manager Not found with ID :" + profileDto.getManager()));
			existedUser.setManager(mananger);
		}
		existedUser.setIsActive(profileDto.getIsActive());
		return repository.save(existedUser);
	}

	@Override
	@Modifying
	public UserProfiles updateUserRole(UUID id, String role) {
		UserProfiles userProfiles = repository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found ID : " + id));
		userProfiles.setRole(handleUpdateRole(role));
		return repository.save(userProfiles);
	}

	@Override
	public String updateStatus(UUID id) {
		UserProfiles userProfile = repository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID : " + id));

		boolean isActive = !userProfile.getIsActive();
		userProfile.setIsActive(isActive);

		repository.save(userProfile);
		return isActive ? "User is Activated" : "User is Deactivated";
	}

	@Override
	public UserProfiles findByName(String name) {
		UserProfiles userProfile = repository.findByName(name)
				.orElseThrow(() -> new UserNotFoundException("User not Found with Name : " + name));
		return userProfile;
	}

	@Override
	@Transactional
	public String deleteUser(String name) {
		UserProfiles userProfile = repository.findByName(name)
				.orElseThrow(() -> new UserNotFoundException("User not Found with Name :" + name));
		repository.removeManagerReference(userProfile.getId());
		repository.delete(userProfile);
		return "deleted successfully";
	}

	private Roles handleUpdateRole(String role) {
		return switch (role) {
		case "EMPLOYEE" -> Roles.EMPLOYEE;
		case "ADMIN" -> Roles.ADMIN;
		case "MANAGER" -> Roles.MANAGER;
		case "FINANCE" -> Roles.FINANCE;
		case "PROCUREMENT_OFFICER" -> Roles.PROCUREMENT_OFFICER;
		case "WAREHOUSE_MANAGER" -> Roles.WAREHOUSE_MANAGER;
		case "SUPPLIER" -> Roles.SUPPLIER;
		case "AUDITOR" -> Roles.AUDITOR;
		default -> throw new IllegalArgumentException("Please specify the allocated Roles");
		};
	}

}
