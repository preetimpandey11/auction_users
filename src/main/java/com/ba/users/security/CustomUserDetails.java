/**
 * 
 */
package com.ba.users.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ba.users.entity.User;

/**
 * @author Preeti Pandey
 *
 */
public class CustomUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1744597050975855557L;

	private String password;
	private final String username;
	private final Collection<? extends GrantedAuthority> authorities;

	public CustomUserDetails(String username, Collection<? extends GrantedAuthority> authorities) {
		this.username = username;
		this.authorities = authorities;
	}

	public CustomUserDetails(String username, String password, Collection<String> authorities) {
		this.username = username;
		this.password = password;
		this.authorities = authorities.stream().map(SimpleGrantedAuthority::new).toList();
	}

	public CustomUserDetails(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.authorities = user.getRoles().stream()
				.flatMap(role -> role.getPrivileges().stream().map(p -> new SimpleGrantedAuthority(p.getCode())))
				.toList();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
