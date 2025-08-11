/**
 * 
 */
package com.ba.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ba.users.entity.UserToken;

/**
 * @author Preeti Pandey
 *
 */
public interface UserTokenRepository extends JpaRepository<UserToken, String> {

	Optional<UserToken> findByTokenKeyAndActiveTrue(String tokenKey);
}
