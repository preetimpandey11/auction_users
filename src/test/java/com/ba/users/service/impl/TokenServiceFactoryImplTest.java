/**
 * 
 */
package com.ba.users.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ba.users.model.TokenType;
import com.ba.users.service.TokenService;

/**
 * @author Preeti Pandey
 *
 */
@ExtendWith(MockitoExtension.class)
public class TokenServiceFactoryImplTest {

	@Mock
	private OpaqueTokenService opagueTokenService;

	@InjectMocks
	private TokenServiceFactoryImpl tokenServiceFactoryImpl;

	@Test
	void testGetTokenService_OpaqueToken() {
		TokenService<Object, Object> tokenService = tokenServiceFactoryImpl.getTokenService(TokenType.OPAQUE);
		assertTrue(tokenService.getClass().equals(OpaqueTokenService.class));
	}

	@Test
	void testGetTokenService_JWTToken() {
		assertThrows(NotImplementedException.class, () -> {
			tokenServiceFactoryImpl.getTokenService(TokenType.JWT);
		});
	}
	
	@Test
	void testGetTokenService_NoType() {
		TokenService<Object, Object> tokenService = tokenServiceFactoryImpl.getTokenService(null);
		assertTrue(tokenService.getClass().equals(OpaqueTokenService.class));
	}

}
