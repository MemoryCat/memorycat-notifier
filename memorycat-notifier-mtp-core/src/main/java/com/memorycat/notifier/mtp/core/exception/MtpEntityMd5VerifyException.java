package com.memorycat.notifier.mtp.core.exception;

import com.memorycat.notifier.mtp.core.entity.MtpEntity;

public class MtpEntityMd5VerifyException extends MtpEntityException {

	private static final long serialVersionUID = 132962117145569196L;

	public MtpEntityMd5VerifyException(MtpEntity mtpEntity, String message, Throwable cause) {
		super(mtpEntity, message, cause);
	}

	public MtpEntityMd5VerifyException(MtpEntity mtpEntity, String message) {
		super(mtpEntity, message);
	}

	public MtpEntityMd5VerifyException(MtpEntity mtpEntity, Throwable cause) {
		super(mtpEntity, cause);
	}

	public MtpEntityMd5VerifyException(MtpEntity mtpEntity) {
		super(mtpEntity);
	}

}
