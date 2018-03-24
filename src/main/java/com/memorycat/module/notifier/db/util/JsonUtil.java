package com.memorycat.module.notifier.db.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.memorycat.module.notifier.db.model.NotificationMessage;

public class JsonUtil {
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	public static String toString(NotificationMessage notificationMessage) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(notificationMessage);
		} catch (JsonProcessingException e) {
			logger.warn(e.getLocalizedMessage(), e);
		}
		return "";
	}

	public static NotificationMessage fromJsonByteArray(byte[] jsonData) {
		if (jsonData == null) {
			throw new NullPointerException();
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(jsonData, NotificationMessage.class);
		} catch (IOException e) {
			logger.warn(e.getLocalizedMessage(), e);
		}
		return null;
	}

}
