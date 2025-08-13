/**
 * 
 */
package com.ba.users.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.StringUtils;

import com.ba.users.entity.User;
import com.ba.users.entity.UserToken;

/**
 * @author Preeti Pandey
 *
 */
@DataJpaTest
class UserTokenRepositoryTest {

	@Autowired
	private UserTokenRepository userTokenRepository;

	@Autowired
	private UserRepository userRepository;

	private UserToken testToken;
	private User testUser;

	private static final String TOKEN_KEY = UUID.randomUUID().toString();

	@BeforeEach
	void setup() {
		testUser = new User();
		testUser.setUsername("testuser");
		testUser.setPassword("password");
		userRepository.save(testUser);
	}

	@AfterEach
	void tearDown() {
		userRepository.delete(testUser);
		if (null != testToken && StringUtils.hasText(testToken.getId())) {
			userTokenRepository.delete(testToken);
		}
	}

	@Test
	void testFindByTokenKeyAndActiveTrue_activeToken() {
		testToken = new UserToken();
		testToken.setActive(true);
		testToken.setTokenKey(TOKEN_KEY);
		testToken.setUser(testUser);
		testToken.setCreatedAt(Instant.now());
		testToken.setExpiresAt(Instant.now());
		userTokenRepository.save(testToken);

		Optional<UserToken> actual = userTokenRepository.findByTokenKeyAndActiveTrue(TOKEN_KEY);
		assert actual.isPresent();
		assertEquals(testToken, actual.get());
		assertEquals(testUser, actual.get().getUser());

	}

	@Test
	void testFindByTokenKeyAndActiveTrue_inactiveToken() {
		testToken = new UserToken();
		testToken.setActive(false);
		testToken.setTokenKey(TOKEN_KEY);
		testToken.setUser(testUser);
		testToken.setCreatedAt(Instant.now());
		testToken.setExpiresAt(Instant.now());
		userTokenRepository.save(testToken);

		Optional<UserToken> actual = userTokenRepository.findByTokenKeyAndActiveTrue(TOKEN_KEY);
		assertTrue(actual.isEmpty());

	}

	@Test
	void testFindByTokenKeyAndActiveTrue_invalidToken() {
		Optional<UserToken> actual = userTokenRepository.findByTokenKeyAndActiveTrue(UUID.randomUUID().toString());
		assertTrue(actual.isEmpty());
	}

	@Test
	void testFindByTokenKeyAndActiveTrue_nullToken() {
		Optional<UserToken> actual = userTokenRepository.findByTokenKeyAndActiveTrue(null);
		assertTrue(actual.isEmpty());
	}

	@Test
	void testFindByTokenKeyAndActiveTrue_blankToken() {
		Optional<UserToken> actual = userTokenRepository.findByTokenKeyAndActiveTrue("");
		assertTrue(actual.isEmpty());
	}

}
