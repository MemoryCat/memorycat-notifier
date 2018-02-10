package com.memorycat.module.notifier.mpush.exception;

import com.memorycat.module.notifier.mpush.model.MPushMessageModel;

public class MPushMessageDeserializeException extends MPushMessageException {

	private static final long serialVersionUID = 6063371856038320349L;

	public MPushMessageDeserializeException(MPushMessageModel mPushMessageModel, String message) {
		super(mPushMessageModel, message);
	}

}
