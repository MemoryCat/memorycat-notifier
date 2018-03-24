package com.memorycat.module.notifier.mpush.model;

public enum MPushMessageType {
	
	

	//认证 1***
	AUTH_ENCRYPT_REQUEST((short)1101),
	AUTH_ENCRYPT_RESPONSE((short)1102),
	AUTH_ENCRYPT_RESPONSE_RETRY((short)1103),
	
	AUTH_LOGIN_REQUEST((short) 1201),
	AUTH_LOGIN_RESPONSE((short) 1202),
	AUTH_LOGIN_RESPONSE_OK((short) 1203),
	AUTH_LOGIN_RESPONSE_NO((short) 1204),
	AUTH_LOGIN_RESPONSE_RETRY((short) 1205),
	
	AUTH_STATE_CHANGE((short) 1301),

	STATE_HEARTBEAT_REQUEST((short)2101),
	STATE_HEARTBEAT_RESPONSE((short)2102),
	STATE_CLIENT_ONLINE((short)2201),
	STATE_CLIENT_BUSY((short)2202),
	STATE_CLIENT_INVISIABLE((short)2203),
	STATE_CLIENT_OFFLINE((short)2204),
	
	MESSAGE_COMMON((short)3000),
	MESSAGE_DATABASE_SERVER_SEND((short)3101),
	MESSAGE_DATABASE_CLIENT_RECV((short)3102),
	MESSAGE_DATABASE_CLIENT_READ((short)3103),
	
	
	
	UNKOWN((short) 0);

	private final Short typeCode;

	private MPushMessageType(short typeCode) {
		this.typeCode = typeCode;
	}

	@Override
	public String toString() {
		return typeCode.toString();
	}

	public Short getTypeCode() {
		return typeCode;
	}

	public static MPushMessageType value(short s) {
		MPushMessageType[] values = MPushMessageType.values();
		if (values != null && values.length > 0) {
			for (MPushMessageType mPushMessageType : values) {
				if (mPushMessageType.getTypeCode().equals(s)) {
					return mPushMessageType;
				}
			}
		}
		return MPushMessageType.UNKOWN;
	}

	public static void main(String[] args) {
		System.out.println(Short.MAX_VALUE);
		System.out.println(Short.MIN_VALUE);
	}
}
