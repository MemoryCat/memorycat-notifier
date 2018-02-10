package com.memorycat.module.notifier.mpush.exception;

import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.util.Constants;

public class UnknownEventException extends MPushMessageException {
	private static final long serialVersionUID = 4379575011975894651L;

	public UnknownEventException(MPushMessageModel mPushMessageModel) {
		super(mPushMessageModel, Constants.EXCEPTION_EVENT_UNKNOWN);
	}

}
