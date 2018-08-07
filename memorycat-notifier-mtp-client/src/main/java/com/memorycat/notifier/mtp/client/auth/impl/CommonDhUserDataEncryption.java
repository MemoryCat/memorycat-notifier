package com.memorycat.notifier.mtp.client.auth.impl;

import java.security.KeyPair;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.notifier.mtp.client.auth.UserDataEncryption;
import com.memorycat.notifier.mtp.core.exception.DencryptException;
import com.memorycat.notifier.mtp.core.exception.EncryptException;
import com.memorycat.notifier.mtp.core.util.Constants;

public class CommonDhUserDataEncryption implements UserDataEncryption {

	private static final Logger logger = LoggerFactory.getLogger(CommonDhUserDataEncryption.class);
	private static final String ALGORITHM = "DES";
	public static final String KEY_ALGORITHM = "DH";

	private DHPublicKey dhPublicKey;
	private DHPrivateKey dhPrivateKey;
	private DHPublicKey serverDhPublicKey;

	public CommonDhUserDataEncryption() {
		super();

	}

	@Override
	public byte[] encrpt(byte[] data) throws EncryptException {
		try {
			KeyAgreement keyAgreement = KeyAgreement.getInstance(KEY_ALGORITHM);
			keyAgreement.init(this.dhPrivateKey);
			keyAgreement.doPhase(this.serverDhPublicKey, true);
			SecretKey secretKey = keyAgreement.generateSecret(ALGORITHM);
			Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage(), e);
			throw new EncryptException(e);
		}
	}

	@Override
	public byte[] decrypt(byte[] data) throws DencryptException {
		try {
			KeyAgreement keyAgreement = KeyAgreement.getInstance(KEY_ALGORITHM);
			keyAgreement.init(this.dhPrivateKey);
			keyAgreement.doPhase(this.serverDhPublicKey, true);
			SecretKey secretKey = keyAgreement.generateSecret(ALGORITHM);
			Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage(), e);
			throw new DencryptException(e);
		}

	}

	@Override
	public void setServerKey(Object key) {
		if (!(key instanceof PublicKey)) {
			throw new IllegalArgumentException(Constants.EXCEPTION_PARAMETER_TYPE_MUSTBE_PUBLICKEY);
		}
		this.serverDhPublicKey = (DHPublicKey) key;

	}

	@Override
	public void setClientKey(Object key) {
		if (!(key instanceof KeyPair)) {
			throw new IllegalArgumentException(Constants.EXCEPTION_PARAMETER_TYPE_MUSTBE_KEYPAIR);
		}
		KeyPair keyPair = (KeyPair) key;
		this.dhPublicKey = (DHPublicKey) keyPair.getPublic();
		this.dhPrivateKey = (DHPrivateKey) keyPair.getPrivate();

	}

	@Override
	public Object getServerKey() {
		return this.serverDhPublicKey;
	}

	public DHPublicKey getDhPublicKey() {
		return dhPublicKey;
	}

	public DHPrivateKey getDhPrivateKey() {
		return dhPrivateKey;
	}

	public DHPublicKey getServerDhPublicKey() {
		return serverDhPublicKey;
	}

}
