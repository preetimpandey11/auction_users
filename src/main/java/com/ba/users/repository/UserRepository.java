/**
 * 
 */
package com.ba.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ba.users.entity.User;

/**
 * @author Preeti Pandey
 *
 */
public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByUsername(String username);

}
