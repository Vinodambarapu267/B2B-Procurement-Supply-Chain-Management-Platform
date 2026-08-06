package com.example.demo.serviceimpl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.UserProfileDto;
import com.example.demo.entity.UserProfiles;
import com.example.demo.repository.UserProfilesRepository;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceimpl implements UserService {
	private final UserProfilesRepository repository;

	@Override
	public UserProfiles createUser(UserProfiles user) {
		repository.findByNameAndRole(user.getName(), user.getRole()).ifPresent(users -> {
			throw new RuntimeException("User Already Exist");
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
	public Optional<UserProfiles> updateUser(UserProfileDto profileDto) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<UserProfiles> updateUserRole(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public String updateStatus(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteUser(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
