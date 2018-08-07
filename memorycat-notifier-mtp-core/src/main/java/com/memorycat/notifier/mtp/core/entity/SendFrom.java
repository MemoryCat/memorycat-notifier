package com.memorycat.notifier.mtp.core.entity;

/**
 * 从哪里发出的消息，服务端？客户端？未知
 * 
 * @author xie
 *
 */
public enum SendFrom {
	UNKNOWN((byte) 0), SERVER((byte) 1), CLIENT((byte) 2);

	private byte side;

	public static SendFrom valueOf(byte b) {
		switch (b) {
		case 1:
			return SERVER;
		case 2:
			return CLIENT;
		default:
			return UNKNOWN;
		}
	}

	private SendFrom(byte side) {
		this.side = side;
	}

	public byte getSide() {
		return side;
	}

}
