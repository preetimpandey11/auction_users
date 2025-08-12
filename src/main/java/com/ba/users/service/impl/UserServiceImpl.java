/**
 * 
 */
package com.ba.users.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ba.users.entity.User;
import com.ba.users.model.UserContactDetails;
import com.ba.users.model.UserNameDetails;
import com.ba.users.model.UserResponse;
import com.ba.users.model.UserSummary;
import com.ba.users.repository.UserRepository;
import com.ba.users.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * @author Preeti Pandey
 *
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final ModelMapper modelMapper;

	@Override
	public UserResponse getUser(String userId) {
		User user = userRepository.findById(userId).orElseThrow();
		return modelMapper.map(user, UserResponse.class);
	}

	@Override
	public UserResponse getUserProfile(String userId) {
		UserNameDetails user = userRepository.findUserById(userId, UserNameDetails.class);
		return modelMapper.map(user, UserResponse.class);
	}

	@Override
	public UserResponse getUserSummary(String userId) {
		UserSummary user = userRepository.findUserById(userId, UserSummary.class);
		return modelMapper.map(user, UserResponse.class);
	}

	@Override
	public UserResponse getUserContact(String userId) {
		UserContactDetails user = userRepository.findUserById(userId, UserContactDetails.class);
		return modelMapper.map(user, UserResponse.class);
	}

}
