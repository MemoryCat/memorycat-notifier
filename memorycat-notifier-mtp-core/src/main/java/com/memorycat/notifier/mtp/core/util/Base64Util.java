package com.memorycat.notifier.mtp.core.util;

import org.apache.mina.util.Base64;

public class Base64Util {

	/**
	 * 编码/解码的次数
	 */
	private static final int CODE_TIMES = 2;

	public static byte[] encode(byte[] data) {
		byte[] returnValue = new byte[data.length];
		System.arraycopy(data, 0, returnValue, 0, data.length);
		for (int i = 0; i < CODE_TIMES; i++) {
			returnValue = Base64.encodeBase64(returnValue);
		}
		return returnValue;
	}

	public static byte[] decode(byte[] data) {
		byte[] returnValue = new byte[data.length];
		System.arraycopy(data, 0, returnValue, 0, data.length);
		for (int i = 0; i < CODE_TIMES; i++) {
			returnValue = Base64.decodeBase64(returnValue);
		}
		return returnValue;
	}
}
