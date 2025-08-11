/**
 * 
 */
package com.ba.users.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ba.users.entity.User;
import com.ba.users.entity.UserToken;

/**
 * @author Preeti Pandey
 *
 */
@DataJpaTest
public class UserTokenRepositoryTest {

	@Autowired
	private UserTokenRepository userTokenRepository;

	@Autowired
	private UserRepository userRepository;

	private UserToken testToken;
	private User testUser;

	private static final String TOKEN_KEY = UUID.randomUUID().toString();

	@BeforeEach
	public void setup() {
		testUser = new User();
		testUser.setUsername("testuser");
		testUser.setPassword("password");
		userRepository.save(testUser);
	}

	@AfterEach
	public void tearDown() {
		userRepository.delete(testUser);
	}

	@Test
	public void testFindByTokenKeyAndActiveTrue_activeToken() {
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
		
		userTokenRepository.delete(testToken);

	}

	@Test
	public void testFindByTokenKeyAndActiveTrue_inactiveToken() {
		testToken = new UserToken();
		testToken.setActive(false);
		testToken.setTokenKey(TOKEN_KEY);
		testToken.setUser(testUser);
		testToken.setCreatedAt(Instant.now());
		testToken.setExpiresAt(Instant.now());
		userTokenRepository.save(testToken);

		Optional<UserToken> actual = userTokenRepository.findByTokenKeyAndActiveTrue(TOKEN_KEY);
		assert actual.isEmpty();
		
		userTokenRepository.delete(testToken);
	}

	@Test
	public void testFindByTokenKeyAndActiveTrue_invalidToken() {
		Optional<UserToken> actual = userTokenRepository.findByTokenKeyAndActiveTrue(UUID.randomUUID().toString());
		assert actual.isEmpty();
	}

	@Test
	public void testFindByTokenKeyAndActiveTrue_nullToken() {
		Optional<UserToken> actual = userTokenRepository.findByTokenKeyAndActiveTrue(null);
		assert actual.isEmpty();
	}

	@Test
	public void testFindByTokenKeyAndActiveTrue_blankToken() {
		Optional<UserToken> actual = userTokenRepository.findByTokenKeyAndActiveTrue("");
		assert actual.isEmpty();
	}

}
