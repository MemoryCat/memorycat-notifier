package com.memorycat.module.notifier.mpush.client.event;

import com.memorycat.module.notifier.mpush.model.MPushMessageModel;

public class HeartBeatResponseEvent extends MPushMessageEvent {

	private static final long serialVersionUID = 1252683987124643239L;

	public HeartBeatResponseEvent(Object source, MPushMessageModel mPushMessageModel) {
		super(source, mPushMessageModel);
		// TODO Auto-generated constructor stub
	}

}
