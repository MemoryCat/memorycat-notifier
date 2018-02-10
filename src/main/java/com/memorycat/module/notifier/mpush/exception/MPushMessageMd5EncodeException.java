package com.memorycat.module.notifier.mpush.exception;

import com.memorycat.module.notifier.mpush.model.MPushMessageModel;

public class MPushMessageMd5EncodeException extends MPushMessageException {

	private static final long serialVersionUID = -5251495193201495062L;

	public MPushMessageMd5EncodeException(MPushMessageModel mPushMessageModel, String message) {
		super(mPushMessageModel, message);
	}

}
