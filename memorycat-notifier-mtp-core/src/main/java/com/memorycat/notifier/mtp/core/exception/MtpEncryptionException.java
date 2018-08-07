package com.memorycat.notifier.mtp.core.exception;

public class MtpEncryptionException extends MemoryCatNotifierException {
	private static final long serialVersionUID = 3361511665116653428L;

	public MtpEncryptionException() {
		super();
	}

	public MtpEncryptionException(String message, Throwable cause) {
		super(message, cause);
	}

	public MtpEncryptionException(String message) {
		super(message);
	}

	public MtpEncryptionException(Throwable cause) {
		super(cause);
	}

}
