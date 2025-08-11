/**
 * 
 */
package com.ba.users.service;

import com.ba.users.model.TokenType;

/**
 * @author Preeti Pandey
 *
 */
public interface TokenServiceFactory {

	<T, R> TokenService<T, R> getTokenServiceImpl(TokenType tokenType);

}