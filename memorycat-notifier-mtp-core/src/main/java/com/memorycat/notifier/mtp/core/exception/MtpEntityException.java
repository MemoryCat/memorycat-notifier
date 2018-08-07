package com.memorycat.notifier.mtp.core.exception;

import com.memorycat.notifier.mtp.core.entity.MtpEntity;

public class MtpEntityException extends MemoryCatNotifierException {

	private static final long serialVersionUID = 8959093076634272255L;

	private final MtpEntity mtpEntity;

	public MtpEntityException(MtpEntity mtpEntity) {
		this.mtpEntity = mtpEntity;
	}

	public MtpEntityException(MtpEntity mtpEntity, String message, Throwable cause) {
		super(message, cause);
		this.mtpEntity = mtpEntity;
	}

	public MtpEntityException(MtpEntity mtpEntity, String message) {
		super(message);
		this.mtpEntity = mtpEntity;
	}

	public MtpEntityException(MtpEntity mtpEntity, Throwable cause) {
		super(cause);
		this.mtpEntity = mtpEntity;
	}

	public MtpEntity getMtpEntity() {
		return mtpEntity;
	}

}
