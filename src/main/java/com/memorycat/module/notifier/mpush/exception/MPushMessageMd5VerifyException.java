package com.memorycat.module.notifier.mpush.exception;

import com.memorycat.module.notifier.mpush.model.MPushMessageModel;

public class MPushMessageMd5VerifyException extends MPushMessageException {
	private static final long serialVersionUID = 2219707635785879881L;

	public MPushMessageMd5VerifyException(MPushMessageModel mPushMessageModel, String message) {
		super(mPushMessageModel, message);
	}

}
