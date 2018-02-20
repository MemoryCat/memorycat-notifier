package com.memorycat.module.notifier.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyUtil {

	private static final Logger logger = LoggerFactory.getLogger(KeyUtil.class);
	private static KeyPairGenerator keyPairGenerator = null;
	static {

		try {
			KeyUtil.keyPairGenerator = KeyPairGenerator.getInstance("dh");
			KeyUtil.keyPairGenerator.initialize(512);
		} catch (NoSuchAlgorithmException e) {
			logger.error("keyPairGenerator初始化失败！！！jre木有dh算法实现！");
		}
	}

	public static synchronized KeyPair getKeyPair() {
		return KeyUtil.keyPairGenerator.generateKeyPair();
	}
}
