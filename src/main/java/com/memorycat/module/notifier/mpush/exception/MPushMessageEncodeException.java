package com.memorycat.module.notifier.mpush.exception;

import com.memorycat.module.notifier.mpush.model.MPushMessageModel;

public class MPushMessageEncodeException extends MPushMessageException {

	private static final long serialVersionUID = -1547382344628694817L;

	public MPushMessageEncodeException(MPushMessageModel mPushMessageModel, String message) {
		super(mPushMessageModel, message);
	}

}
