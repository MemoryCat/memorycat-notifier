package com.memorycat.notifier.mtp.core.exception;

public class UnformatMessageException extends MemoryCatNotifierException {

	private static final long serialVersionUID = 1142307540462797175L;
	private final Object msg;

	public UnformatMessageException(Object msg) {
		this.msg = msg;
	}

	public Object getMsg() {
		return msg;
	}

}
