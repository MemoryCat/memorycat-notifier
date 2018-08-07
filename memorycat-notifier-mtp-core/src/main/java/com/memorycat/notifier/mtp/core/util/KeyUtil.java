package com.memorycat.notifier.mtp.core.util;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;

import javax.crypto.spec.DHPublicKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.notifier.mtp.core.entity.auth.EncryptionDto;
import com.memorycat.notifier.mtp.core.exception.MtpEncryptionException;

public class KeyUtil {
	private static final String DH = "dh";
	private static final Logger logger = LoggerFactory.getLogger(KeyUtil.class);

	public static KeyPair generateKeyPair() throws MtpEncryptionException {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(DH);
			keyPairGenerator.initialize(1024);
			return keyPairGenerator.generateKeyPair();
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage(), e);
			throw new MtpEncryptionException(e);
		}
	}

	public static PublicKey getPublicKeyFromEncryptionDto(EncryptionDto encryptionDto) throws MtpEncryptionException {
		if (encryptionDto == null) {
			throw new NullPointerException();
		}
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(encryptionDto.getEncryptionType().name());
			return keyFactory
					.generatePublic(new DHPublicKeySpec(new BigInteger((String) encryptionDto.getData().get("y")),
							new BigInteger((String) encryptionDto.getData().get("p")),
							new BigInteger((String) encryptionDto.getData().get("g"))));
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage(), e);
			throw new MtpEncryptionException(e);
		}

	}

}
