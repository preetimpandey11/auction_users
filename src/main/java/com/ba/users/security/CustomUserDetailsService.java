/**
 * 
 */
package com.ba.users.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ba.users.entity.User;
import com.ba.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author Preeti Pandey
 *
 */
@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Unable to found user: " + username));
		return new CustomUserDetails(user);
	}

}
