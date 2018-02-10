package com.memorycat.module.notifier.mpush.event;

import java.util.Date;
import java.util.EventObject;

import com.memorycat.module.notifier.mpush.model.MPushMessageModel;

public class MPushEvent extends EventObject {
	private static final long serialVersionUID = -2929860097140755068L;

	private final Date eventDate = new Date();
	private final Object context;

	public Date getEventDate() {
		return eventDate;
	}

	
	@Override
	public MPushMessageModel getSource() {
		return (MPushMessageModel) super.getSource();
	}


	public MPushEvent(MPushMessageModel source, Object context) {
		super(source);
		this.context = context;
	}

	public Object getContext() {
		return context;
	}

}
