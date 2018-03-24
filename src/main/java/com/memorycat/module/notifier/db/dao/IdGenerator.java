package com.memorycat.module.notifier.db.dao;

import java.util.UUID;

public class IdGenerator {

	public static String generateUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
