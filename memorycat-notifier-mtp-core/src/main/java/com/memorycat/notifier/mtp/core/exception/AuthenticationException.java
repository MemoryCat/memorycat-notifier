package com.memorycat.notifier.mtp.core.exception;

public class AuthenticationException extends MemoryCatNotifierException {
	private static final long serialVersionUID = 3709182961969001515L;

	public AuthenticationException() {
		super();
	}

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenticationException(String message) {
		super(message);
	}

	public AuthenticationException(Throwable cause) {
		super(cause);
	}

}
