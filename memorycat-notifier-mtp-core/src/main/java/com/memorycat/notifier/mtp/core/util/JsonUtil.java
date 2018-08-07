package com.memorycat.notifier.mtp.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memorycat.notifier.mtp.core.entity.auth.EncryptionDto;
import com.memorycat.notifier.mtp.core.exception.JsonException;

public class JsonUtil {
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	public static String toString(Object o) throws JsonException {
		try {
			return new ObjectMapper().writeValueAsString(o);
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage(), e);
			throw new JsonException(e);
		}
	}

	public static byte[] toByteArray(Object o) throws JsonException {
		try {
			return new ObjectMapper().writeValueAsBytes(o);
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage(), e);
			throw new JsonException(e);
		}
	}

	public static EncryptionDto fromByteArray(byte[] data) throws JsonException {
		try {
			return new ObjectMapper().readValue(data, EncryptionDto.class);
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage(), e);
			throw new JsonException(e);
		}
	}

	public static <T> T toObject(byte[] data, Class<T> clazz) throws JsonException {
		try {
			return new ObjectMapper().readValue(data, clazz);
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage(), e);
			throw new JsonException(e);
		}
	}

}
