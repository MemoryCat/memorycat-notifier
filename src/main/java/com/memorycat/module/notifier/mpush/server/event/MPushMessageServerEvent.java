package com.memorycat.module.notifier.mpush.server.event;

import java.util.Date;
import java.util.EventObject;

import com.memorycat.module.notifier.mpush.server.MPushMessageServer;

public class MPushMessageServerEvent extends EventObject {

	private static final long serialVersionUID = 6430885687005466962L;
	final private MPushMessageServer mPushMessageServer;
	final private Date eventTime = new Date();

	public MPushMessageServerEvent(MPushMessageServer mPushMessageServer) {
		super(mPushMessageServer);
		this.mPushMessageServer = mPushMessageServer;
	}

	public MPushMessageServer getmPushMessageServer() {
		return mPushMessageServer;
	}

	public Date getEventTime() {
		return eventTime;
	}

}
