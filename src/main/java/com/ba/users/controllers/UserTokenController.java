/**
 * 
 */
package com.ba.users.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.ba.users.api.TokensApi;
import com.ba.users.model.LoginRequest;
import com.ba.users.model.TokenDetailsResponse;
import com.ba.users.model.TokenType;
import com.ba.users.model.UserDetailsResponse;
import com.ba.users.service.TokenServiceFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Preeti Pandey
 *
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserTokenController implements TokensApi {

	private final TokenServiceFactory tokenServiceFactory;

	private final AuthenticationManager authenticationManager;

	@Override
	public ResponseEntity<TokenDetailsResponse> generateToken(LoginRequest loginRequest) {
		log.trace("Token generation has started for the user.");

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = (String) tokenServiceFactory.getTokenService(TokenType.OPAQUE).generate();
		TokenDetailsResponse response = new TokenDetailsResponse();
		response.setToken(token);

		log.trace("Token generated successfully for the user.");
		String cookie = "token=" + token + "; HttpOnly; Path=/; SameSite=Strict";
		return ResponseEntity.status(HttpStatus.CREATED.value()).header("Set-Cookie", cookie)
				.header("X-Auth-Token", token).header("Authorization", "Bearer " + token).body(response);

	}

	@Override
	public ResponseEntity<UserDetailsResponse> authorizeUserToken() {
		log.trace("Validating the user token");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = (String) auth.getPrincipal();
		List<String> privs = auth.getAuthorities().stream().map(x -> x.getAuthority()).collect(Collectors.toList());
		UserDetailsResponse response = new UserDetailsResponse();
		response.setId(id);
		response.setScopes(privs);
		log.trace("The user token has been successfully validated.");
		return ResponseEntity.ok().body(response);
	}

}
