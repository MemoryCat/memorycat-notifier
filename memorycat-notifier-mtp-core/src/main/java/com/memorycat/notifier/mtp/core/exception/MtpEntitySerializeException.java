package com.memorycat.notifier.mtp.core.exception;

import com.memorycat.notifier.mtp.core.entity.MtpEntity;

public class MtpEntitySerializeException extends MtpEntityException {

	private static final long serialVersionUID = -7519572638742383700L;

	public MtpEntitySerializeException(MtpEntity mtpEntity, String message, Throwable cause) {
		super(mtpEntity, message, cause);
	}

	public MtpEntitySerializeException(MtpEntity mtpEntity, String message) {
		super(mtpEntity, message);
	}

	public MtpEntitySerializeException(MtpEntity mtpEntity, Throwable cause) {
		super(mtpEntity, cause);
	}

	public MtpEntitySerializeException(MtpEntity mtpEntity) {
		super(mtpEntity);
	}

}
