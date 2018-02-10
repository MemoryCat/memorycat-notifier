package com.memorycat.module.notifier.util;

public class MPushMessageModelConstants {
	public static final String MESSAGE_MARK = "MemoryCatNotifier";

	public static final int SIZE_MESSAGE_MARK = MESSAGE_MARK.length();
	public static final int SIZE_MESSAGE_VERSION = 1;
	public static final int SIZE_MESSAGE_TYPE = 2;
	public static final int SIZE_MESSAGE_TIMESTAMP = 8;
	public static final int SIZE_MESSAGE_SEQUENCE_REQUEST = 8;
	public static final int SIZE_MESSAGE_SEQUENCE_RESPONSE = 8;
	public static final int SIZE_MESSAGE_BODY_LENGTH = 2;
	public static final int SIZE_MESSAGE_MD5 = 16;

	public static final int OFFSET_MARK_MEMORYCATNOTIFIER = 0;
	public static final int OFFSET_MESSAGE_VERSION = SIZE_MESSAGE_MARK;
	public static final int OFFSET_MESSAGE_TYPE = OFFSET_MESSAGE_VERSION + SIZE_MESSAGE_VERSION;
	public static final int OFFSET_MESSAGE_TIMESTAMP = OFFSET_MESSAGE_TYPE + SIZE_MESSAGE_TYPE;
	public static final int OFFSET_MESSAGE_SEQUENCE_REQUEST = OFFSET_MESSAGE_TIMESTAMP + SIZE_MESSAGE_TIMESTAMP;
	public static final int OFFSET_MESSAGE_SEQUENCE_RESPONSE = OFFSET_MESSAGE_SEQUENCE_REQUEST
			+ SIZE_MESSAGE_SEQUENCE_REQUEST;
	public static final int OFFSET_MESSAGE_BODY_LENGTH = OFFSET_MESSAGE_SEQUENCE_RESPONSE
			+ SIZE_MESSAGE_SEQUENCE_RESPONSE;
	public static final int OFFSET_MESSAGE_MD5 = OFFSET_MESSAGE_BODY_LENGTH + SIZE_MESSAGE_BODY_LENGTH;
	public static final int OFFSET_MESSAGE_BODY = OFFSET_MESSAGE_MD5 + SIZE_MESSAGE_MD5;
}
