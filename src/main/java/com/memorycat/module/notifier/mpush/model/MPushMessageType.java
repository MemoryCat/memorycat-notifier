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
