package com.memorycat.notifier.mtp.core.exception;

import com.memorycat.notifier.mtp.core.entity.MtpEntity;

public class MtpEntityUnSerializeException extends MtpEntityException {

	private static final long serialVersionUID = 1755877951030759885L;

	public MtpEntityUnSerializeException(MtpEntity mtpEntity, String message, Throwable cause) {
		super(mtpEntity, message, cause);
	}

	public MtpEntityUnSerializeException(MtpEntity mtpEntity, String message) {
		super(mtpEntity, message);
	}

	public MtpEntityUnSerializeException(MtpEntity mtpEntity, Throwable cause) {
		super(mtpEntity, cause);
	}

	public MtpEntityUnSerializeException(MtpEntity mtpEntity) {
		super(mtpEntity);
	}

}
