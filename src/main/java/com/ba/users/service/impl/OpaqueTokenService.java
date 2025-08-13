/**
 * 
 */
package com.ba.users.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.ba.users.entity.Privilege;
import com.ba.users.entity.User;
import com.ba.users.entity.UserToken;
import com.ba.users.exception.InvalidTokenException;
import com.ba.users.repository.UserRepository;
import com.ba.users.repository.UserTokenRepository;
import com.ba.users.security.CustomUserDetails;
import com.ba.users.service.TokenService;

import lombok.RequiredArgsConstructor;

/**
 * @author Preeti Pandey
 *
 */
@RequiredArgsConstructor
@Service
public class OpaqueTokenService implements TokenService<String, CustomUserDetails> {

	private final UserTokenRepository userTokenRepository;

	private final UserRepository userRepository;

	@Override
	public CustomUserDetails validate(String token) {
		UserToken userToken = userTokenRepository.findByTokenKeyAndActiveTrue(token)
				.orElseThrow(InvalidTokenException::new);

		return new CustomUserDetails(userToken.getUser().getId(),
				userToken.getUser().getRoles().stream().flatMap(r -> r.getPrivileges().stream()).map(Privilege::getCode)
						.map(SimpleGrantedAuthority::new).toList());
	}

	@Override
	public String generate() {
		String username = getPrincipalFromUsernamePasswordAuthenticationToken();
		User user = userRepository.findByUsername(username).orElseThrow();
		String tokenKey = UUID.randomUUID().toString();
		Instant issuedAt = Instant.now();
		Instant expiresAt = issuedAt.plus(2, ChronoUnit.HOURS);
		UserToken userToken = UserToken.builder().active(true).createdAt(issuedAt).expiresAt(expiresAt).user(user)
				.tokenKey(tokenKey).build();
		userTokenRepository.save(userToken);
		return tokenKey;
	}

}
