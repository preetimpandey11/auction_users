package com.ba.users.exception;

public class InvalidTokenException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8938442431310366515L;

	public InvalidTokenException() {
		super("The token is invalid.");
	}

}
