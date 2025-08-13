/**
 * 
 */
package com.ba.users.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ba.users.entity.User;

/**
 * @author Preeti Pandey
 *
 */
@DataJpaTest
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	private User testUser;

	@BeforeEach
	void setUp() {
		testUser = new User();
		testUser.setUsername("testuser");
		testUser.setPassword("password");
		userRepository.save(testUser);
	}

	@AfterEach
	void tearDown() {
		userRepository.delete(testUser);
	}

	@Test
	void testFindByUsername_validInput() {
		Optional<User> retrievedUser = userRepository.findByUsername("testuser");
		assert retrievedUser.isPresent();
		assertEquals(testUser, retrievedUser.get());
	}

	@Test
	void testFindByUsername_nullInput() {
		Optional<User> retrievedUser = userRepository.findByUsername(null);
		assertTrue(retrievedUser.isEmpty());
	}

	@Test
	void testFindByUsername_blankInput() {
		Optional<User> retrievedUser = userRepository.findByUsername("");
		assertTrue(retrievedUser.isEmpty());
	}

	@Test
	void testFindByUsername_invalidInput() {
		Optional<User> retrievedUser = userRepository.findByUsername("invaliduser");
		assertTrue(retrievedUser.isEmpty());
	}

}
