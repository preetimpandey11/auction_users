/**
 * 
 */
package com.ba.users.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.ba.users.api.UsersApi;
import com.ba.users.model.UserResponse;
import com.ba.users.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Preeti Pandey
 *
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {

	private final UserService userService;

	@Override
	@PreAuthorize("#userId == authentication.principal")
	public ResponseEntity<UserResponse> getUser(String userId) {
		return ResponseEntity.ok().body(userService.getUser(userId));

	}

	@Override
	@PreAuthorize("hasAuthority('user:read_summary')")
	public ResponseEntity<UserResponse> getUserSummary(String userId) {
		return ResponseEntity.ok().body(userService.getUserSummary(userId));
	}

	@Override
	@PreAuthorize("hasAuthority('user:read_contact')")
	public ResponseEntity<UserResponse> getUserContact(String userId) {
		return ResponseEntity.ok().body(userService.getUserContact(userId));
	}

	@Override
	@PreAuthorize("hasAuthority('user:read_profile')")
	public ResponseEntity<UserResponse> getUserProfile(String userId) {
		return ResponseEntity.ok().body(userService.getUserProfile(userId));
	}

}
