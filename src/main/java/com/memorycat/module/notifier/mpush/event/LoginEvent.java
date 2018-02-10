package com.memorycat.module.notifier.mpush.event;

import com.memorycat.module.notifier.mpush.model.MPushMessageModel;

public class LoginEvent extends MPushEvent {

	private static final long serialVersionUID = -8950909624688474825L;

	public LoginEvent(MPushMessageModel source, Object context) {
		super(source, context);
	}

}
