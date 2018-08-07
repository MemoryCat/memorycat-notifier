package com.memorycat.notifier.mtp.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

	private static MessageDigest messageDigest;

	private static MessageDigest getInstance() throws NoSuchAlgorithmException {
		if (Md5Util.messageDigest == null) {
			Md5Util.messageDigest = MessageDigest.getInstance("md5");
		}
		return Md5Util.messageDigest;
	}

	public static byte[] encode(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest instance = Md5Util.getInstance();
		// 好像说MessageDigest不是线程安全的
		synchronized (instance) {
			return instance.digest(data);
		}
	}

}
