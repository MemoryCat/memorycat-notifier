package com.memorycat.module.notifier.mpush.core;

import java.util.Date;
import java.util.EventObject;

public class MPushEvent extends EventObject {
	public MPushEvent(Object source) {
		super(source);
	}

	private static final long serialVersionUID = -2929860097140755068L;

	private final Date eventDate = new Date();

	public Date getEventDate() {
		return eventDate;
	}

}
