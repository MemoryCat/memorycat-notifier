package com.memorycat.module.notifier.mpush.exception;

import com.memorycat.module.notifier.mpush.model.MPushMessageModel;

public class MPushMessageDecodeException extends MPushMessageException {

	private static final long serialVersionUID = 1191198128711393585L;

	public MPushMessageDecodeException(MPushMessageModel mPushMessageModel, String message) {
		super(mPushMessageModel, message);
	}

}
