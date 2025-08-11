/**
 * 
 */
package com.ba.users.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ba.users.entity.User;
import com.ba.users.entity.UserToken;
import com.ba.users.exception.InvalidTokenException;
import com.ba.users.repository.UserRepository;
import com.ba.users.repository.UserTokenRepository;
import com.ba.users.security.CustomUserDetails;

/**
 * @author Preeti Pandey
 *
 */

@ExtendWith(MockitoExtension.class)
public class OpaqueTokenServiceTest {

	@Mock
	private UserTokenRepository userTokenRepository;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private OpaqueTokenService tokenService;

	@BeforeEach
	public void setup() {
		SecurityContextHolder.createEmptyContext();
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("testuser", "pass"));
	}

	@AfterEach
	public void teardown() {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void testGenerate_success() {

		User testuser = new User();
		testuser.setUsername("testUser");

		Mockito.when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testuser));

		String token = tokenService.generate();
		assertNotNull(token);

	}

	@Test
	public void testGenerate_userNotFound() {
		Mockito.when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
		assertThrows(NoSuchElementException.class, () -> tokenService.generate());

	}

	@Test
	public void testValidate_success() {

		UserToken token = new UserToken();
		token.setTokenKey("testuser");
		token.setUser(new User());

		Mockito.when(userTokenRepository.findByTokenKeyAndActiveTrue("testuser")).thenReturn(Optional.of(token));

		CustomUserDetails actual = tokenService.validate("testuser");
		assertNotNull(actual);

	}

	@Test
	public void testValidate_tokenNotFound() {
		Mockito.when(userTokenRepository.findByTokenKeyAndActiveTrue("testuser")).thenReturn(Optional.empty());
		assertThrows(InvalidTokenException.class, () -> tokenService.validate("testuser"));

	}

}
