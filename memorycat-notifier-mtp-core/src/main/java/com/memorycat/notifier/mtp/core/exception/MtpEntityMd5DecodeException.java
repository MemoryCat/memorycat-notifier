package com.memorycat.notifier.mtp.core.exception;

import com.memorycat.notifier.mtp.core.entity.MtpEntity;

public class MtpEntityMd5DecodeException extends MtpEntityException {

	private static final long serialVersionUID = -6301830400107663473L;

	public MtpEntityMd5DecodeException(MtpEntity mtpEntity, String message, Throwable cause) {
		super(mtpEntity, message, cause);
	}

	public MtpEntityMd5DecodeException(MtpEntity mtpEntity, String message) {
		super(mtpEntity, message);
	}

	public MtpEntityMd5DecodeException(MtpEntity mtpEntity, Throwable cause) {
		super(mtpEntity, cause);
	}

	public MtpEntityMd5DecodeException(MtpEntity mtpEntity) {
		super(mtpEntity);
	}

}
