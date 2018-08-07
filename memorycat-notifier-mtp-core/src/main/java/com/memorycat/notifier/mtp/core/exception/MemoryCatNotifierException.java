package com.memorycat.notifier.mtp.core.exception;

public class MemoryCatNotifierException extends Exception {

	private static final long serialVersionUID = 6813094349464892415L;

	public MemoryCatNotifierException() {
		super();
	}

	public MemoryCatNotifierException(String message, Throwable cause) {
		super(message, cause);
	}

	public MemoryCatNotifierException(String message) {
		super(message);
	}

	public MemoryCatNotifierException(Throwable cause) {
		super(cause);
	}
}
