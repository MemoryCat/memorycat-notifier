package com.memorycat.notifier.mtp.core.exception;

import com.memorycat.notifier.mtp.core.entity.MtpEntity;

public class MtpEntityMd5EncodeException extends MtpEntityException {

	private static final long serialVersionUID = 1447723122610132263L;

	public MtpEntityMd5EncodeException(MtpEntity mtpEntity, String message, Throwable cause) {
		super(mtpEntity, message, cause);
	}

	public MtpEntityMd5EncodeException(MtpEntity mtpEntity, String message) {
		super(mtpEntity, message);
	}

	public MtpEntityMd5EncodeException(MtpEntity mtpEntity, Throwable cause) {
		super(mtpEntity, cause);
	}

	public MtpEntityMd5EncodeException(MtpEntity mtpEntity) {
		super(mtpEntity);
	}

}
