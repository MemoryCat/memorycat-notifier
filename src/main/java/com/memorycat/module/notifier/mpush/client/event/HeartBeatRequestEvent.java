package com.memorycat.module.notifier.mpush.client.event;

import com.memorycat.module.notifier.mpush.model.MPushMessageModel;

public class HeartBeatRequestEvent extends MPushMessageEvent {

	private static final long serialVersionUID = -1043681698779603205L;

	public HeartBeatRequestEvent(Object source, MPushMessageModel mPushMessageModel) {
		super(source, mPushMessageModel);
	}

}
