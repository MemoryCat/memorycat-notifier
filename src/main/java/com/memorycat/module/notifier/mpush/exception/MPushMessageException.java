package com.memorycat.module.notifier.mpush.exception;

import com.memorycat.module.notifier.exception.NotifierException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;

public class MPushMessageException extends NotifierException {

	private static final long serialVersionUID = -8219045261243661133L;

	private final MPushMessageModel mPushMessageModel;

	public MPushMessageException(MPushMessageModel mPushMessageModel, String message) {
		super(message);
		this.mPushMessageModel = mPushMessageModel;
	}

	public MPushMessageModel getmPushMessageModel() {
		return mPushMessageModel;
	}

}
