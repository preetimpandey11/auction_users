/**
 * 
 */
package com.ba.users.service.impl;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

import com.ba.users.model.TokenType;
import com.ba.users.service.TokenService;
import com.ba.users.service.TokenServiceFactory;

import lombok.RequiredArgsConstructor;

/**
 * @author Preeti Pandey
 *
 */
@Component
@RequiredArgsConstructor
public class TokenServiceFactoryImpl implements TokenServiceFactory {

	private final OpaqueTokenService opagueTokenService;

	@Override
	public <T, R> TokenService<T, R> getTokenService(TokenType tokenType) {
		if (null != tokenType) {
			switch (tokenType) {
			case JWT:
				throw new NotImplementedException("The JWT is not implemented yet.");
			case OPAQUE:
				return (TokenService<T, R>) opagueTokenService;
			}
		}
		return (TokenService<T, R>) opagueTokenService;
	}

}
