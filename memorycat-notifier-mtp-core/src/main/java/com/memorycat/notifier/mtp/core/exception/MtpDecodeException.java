package com.memorycat.notifier.mtp.core.exception;

public class MtpDecodeException extends MemoryCatNotifierException {

	private static final long serialVersionUID = 8831004523446209027L;

	public MtpDecodeException() {
		super();
	}

	public MtpDecodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public MtpDecodeException(String message) {
		super(message);
	}

	public MtpDecodeException(Throwable cause) {
		super(cause);
	}

}
