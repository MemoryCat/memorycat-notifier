package com.memorycat.notifier.mtp.core.entity;

public enum MessageType {

	// 认证 1***
	AUTH_ENCRYPT_REQUEST((short) 1101), 
	AUTH_ENCRYPT_RESPONSE((short) 1102), 
	AUTH_ENCRYPT_RESPONSE_RETRY((short) 1103),

	
	AUTH_LOGIN_REQUEST((short) 1201), 
	AUTH_LOGIN_RESPONSE((short) 1202), 
	AUTH_LOGIN_RESPONSE_OK((short) 1203),
	AUTH_LOGIN_RESPONSE_NO((short) 1204), 
	AUTH_LOGIN_RESPONSE_RETRY((short) 1205),

	// 心跳 2***
	STATE_HEARTBEAT_REQUEST((short) 2101),
	STATE_HEARTBEAT_RESPONSE((short) 2102), 
	STATE_CLIENT_ONLINE((short) 2201),
	STATE_CLIENT_BUSY(	(short) 2202),
	STATE_CLIENT_INVISIABLE((short) 2203), 
	STATE_CLIENT_OFFLINE((short) 2204),

	// 通讯信息 3***
	MESSAGE_COMMON((short) 3000), 
	MESSAGE_COMMON_TEXT((short) 3001), 
	MESSAGE_COMMON_BINARAY((short) 3002), 
	MESSAGE_COMMON_CLIENT_RECV((short) 3102),
	MESSAGE_COMMON_CLIENT_READ((short) 3103),
	MESSAGE_FILE_SERVER_REQUEST_FROM_CLIENT((short)3201),
	MESSAGE_FILE_SERVER_RESPONSE_TO_CLIENT((short)3204),
	MESSAGE_FILE_CLIENT_REQUEST_FROM_SERVER((short)3203),
	MESSAGE_FILE_CLIENT_RESPONSE_TO_SERVER((short)3202),
	MESSAGE_NOTIFICATION_SERVER_SEND((short)3301),
	MESSAGE_NOTIFICATION_CLIENT_RECV((short)3302),
	MESSAGE_NOTIFICATION_CLIENT_READ((short)3303),
	

	UNKOWN((short) 0);

	private final Short typeCode;

	private MessageType(short typeCode) {
		this.typeCode = typeCode;
	}

	public Short getTypeCode() {
		return typeCode;
	}

	public static MessageType valueOf(short s) {
		MessageType[] values = MessageType.values();
		if (values != null && values.length > 0) {
			for (MessageType mPushMessageType : values) {
				if (mPushMessageType.getTypeCode().equals(s)) {
					return mPushMessageType;
				}
			}
		}
		return MessageType.UNKOWN;
	}
}
