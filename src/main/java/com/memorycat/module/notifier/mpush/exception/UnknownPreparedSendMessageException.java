package com.memorycat.module.notifier.mpush.exception;

import com.memorycat.module.notifier.exception.NotifierException;

public class UnknownPreparedSendMessageException extends NotifierException {

	private static final long serialVersionUID = -5423887611982155369L;

	private final Object context;
	private final Object preparedMessage;

	public UnknownPreparedSendMessageException(Object context, Object preparedMessage) {
		super();
		this.context = context;
		this.preparedMessage = preparedMessage;
	}

	public Object getContext() {
		return context;
	}

	public Object getPreparedMessage() {
		return preparedMessage;
	}

}
