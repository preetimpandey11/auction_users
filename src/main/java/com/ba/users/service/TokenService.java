/**
 * 
 */
package com.ba.users.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ba.users.exception.InvalidTokenException;
import com.ba.users.security.CustomUserDetails;

/**
 * @author Preeti Pandey
 *
 */
public interface TokenService<T, R> {

	default String getPrincipalFromUsernamePasswordAuthenticationToken() {
		UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
				.getContext().getAuthentication();
		CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
		return principal.getUsername();
	}

	T generate();

	R validate(T token) throws InvalidTokenException;

}
