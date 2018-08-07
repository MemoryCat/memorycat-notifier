package com.memorycat.notifier.mtp.core.util;

import java.util.UUID;

public class UuidUtil {

	public static String generateUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
