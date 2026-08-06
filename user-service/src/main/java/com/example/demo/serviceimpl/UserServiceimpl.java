package com.example.demo.serviceimpl;

import java.util.Optional;

import com.example.demo.dto.UserProfileDto;
import com.example.demo.entity.UserProfiles;
import com.example.demo.service.UserService;

public class UserServiceimpl implements UserService {

	@Override
	public UserProfiles createUser(UserProfiles user) {
		// TODO Auto-generated method stub
		return null;
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
