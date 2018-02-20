package com.memorycat.module.notifier.mpush.client.event;

import com.memorycat.module.notifier.mpush.core.MPushEvent;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;

public class MPushMessageEvent extends MPushEvent {
	private static final long serialVersionUID = -8950909624688474825L;

	private final MPushMessageModel mPushMessageModel;

	public MPushMessageEvent(Object source, MPushMessageModel mPushMessageModel) {
		super(source);
		this.mPushMessageModel = mPushMessageModel;
	}

	public MPushMessageModel getmPushMessageModel() {
		return mPushMessageModel;
	}

}
