package com.memorycat.module.notifier.exception;

public class NotifierException extends Exception {

	private static final long serialVersionUID = -9222470446981641758L;

	public NotifierException() {
		super();
	}

	public NotifierException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotifierException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotifierException(String message) {
		super(message);
	}

	public NotifierException(Throwable cause) {
		super(cause);
	}

}
