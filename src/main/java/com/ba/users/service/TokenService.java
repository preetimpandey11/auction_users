/**
 * 
 */
package com.ba.users.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ba.users.exception.InvalidTokenException;

/**
 * @author Preeti Pandey
 *
 */
public interface TokenService<T, R> {

	default String getPrincipalFromUsernamePasswordAuthenticationToken() {
		UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
				.getContext().getAuthentication();
		return (String) authentication.getPrincipal();
	}

	T generate();

	R validate(T token) throws InvalidTokenException;

}
