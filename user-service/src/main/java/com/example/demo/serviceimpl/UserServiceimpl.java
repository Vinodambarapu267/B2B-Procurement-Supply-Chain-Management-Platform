package com.example.demo.serviceimpl;

import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	public UserProfiles updateUser(UUID id, UserProfileDto profileDto) {
		UserProfiles existedUser = repository.findById(id).orElseThrow(() -> new RuntimeException("User not Found"));
		existedUser.setName(profileDto.getName());
		existedUser.setDepartment(profileDto.getDepartment());
		existedUser.setJobTitle(profileDto.getJobTitle());
		existedUser.setRole(profileDto.getRole());
		if (profileDto.getManager() != null) {
			UserProfiles mananger = repository.findById(profileDto.getManager())
					.orElseThrow(() -> new RuntimeException("Manager Not found"));
			existedUser.setManager(mananger);
		}
		existedUser.setIsActive(profileDto.getIsActive());
		return repository.save(existedUser);
	}

	@Override
	public UserProfiles updateUserRole(Long id) {
		// TODO Auto-generated method stub
		return null;
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
