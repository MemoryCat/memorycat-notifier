package com.memorycat.module.notifier.mpush.server.event;

import com.memorycat.module.notifier.mpush.server.MPushMessageServer;
import com.memorycat.module.notifier.mpush.server.model.LoginUser;

public class LoginUserEvent extends MPushMessageServerEvent {

	private static final long serialVersionUID = 7526574503659708455L;
	final private LoginUser loginUser;
	final private EventType eventType;

	static public enum EventType {
		ADD_USER, REMOVE_USER
	}

	public LoginUserEvent(MPushMessageServer mPushMessageServer, LoginUser loginUser, EventType eventType) {
		super(mPushMessageServer);
		this.loginUser = loginUser;
		this.eventType = eventType;
	}

	public LoginUser getLoginUser() {
		return loginUser;
	}

	public EventType getEventType() {
		return eventType;
	}

}
