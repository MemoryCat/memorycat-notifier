package com.memorycat.module.notifier.db.exception;

import com.memorycat.module.notifier.mpush.exception.MPushMessageException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;

public class NotificationMessageSerializeException extends MPushMessageException {
	private static final long serialVersionUID = -5762704630024410153L;

	public NotificationMessageSerializeException(MPushMessageModel mPushMessageModel, String message) {
		super(mPushMessageModel, message);
	}

}
