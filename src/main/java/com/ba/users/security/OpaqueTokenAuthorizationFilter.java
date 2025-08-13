/**
 * 
 */
package com.ba.users.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ba.users.exception.InvalidTokenException;
import com.ba.users.model.TokenType;
import com.ba.users.service.TokenServiceFactory;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * @author Preeti Pandey
 *
 */
@RequiredArgsConstructor
@Component
public class OpaqueTokenAuthorizationFilter extends OncePerRequestFilter {

	private final TokenServiceFactory tokenServiceFactory;

	private static final String AUTH_HEADER = "Authorization";
	private static final String AUTH_TYPE = "Bearer";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String token = extractAuthorizationHeader(request);

		if (token == null) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			CustomUserDetails userDetails = (CustomUserDetails) tokenServiceFactory.getTokenService(TokenType.OPAQUE)
					.validate(token);
			final SecurityContext context = SecurityContextHolder.createEmptyContext();
			final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					userDetails.getUsername(), null, userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			context.setAuthentication(authentication);
			SecurityContextHolder.setContext(context);
			filterChain.doFilter(request, response);
		} catch (InvalidTokenException ex) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write(ex.getMessage());
		}
	}

	private String extractAuthorizationHeader(HttpServletRequest request) {
		final String headerValue = request.getHeader(AUTH_HEADER);

		if (headerValue == null || !headerValue.startsWith(AUTH_TYPE)) {
			return null;
		}

		return headerValue.substring(AUTH_TYPE.length()).trim();
	}

}
