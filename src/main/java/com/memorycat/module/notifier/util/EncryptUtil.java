package com.memorycat.module.notifier.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.module.notifier.exception.EncryptDecodeException;
import com.memorycat.module.notifier.exception.EncryptEncodeException;

public class EncryptUtil {

	private static final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);
	private static final String ALGORITHM = "DES";
	public static final String KEY_ALGORITHM = "DH";
	public static final String PROVIDER = "BC";
	static {
//		Security.insertProviderAt(new BouncyCastleProvider(),1);
	}
	private static KeyPairGenerator keyPairGenerator = init();

	private static KeyPairGenerator init() {
		try {
//			KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM, EncryptUtil.PROVIDER);
			KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			// kpg.initialize(512);
			return kpg;
		} catch (Exception e) {
			logger.error("keyPairGenerator初始化失败！！！！", e);
			throw new RuntimeException(e);
		}
	}

	public static synchronized KeyPair getKeyPair() {
		return EncryptUtil.keyPairGenerator.generateKeyPair();
	}

	public static byte[] encode(PublicKey publicKey, PrivateKey privateKey, byte[] data) throws EncryptEncodeException {
//TODO 在安卓下有bug，目前先注释掉，日后再找解决方法
		return data;
		
//		if (publicKey == null || privateKey == null) {
//			throw new NullPointerException();
//		}
//
//		try {
////			KeyAgreement keyAgreement = KeyAgreement.getInstance(KEY_ALGORITHM, PROVIDER);
//			KeyAgreement keyAgreement = KeyAgreement.getInstance(KEY_ALGORITHM);
//			keyAgreement.init(privateKey);
//			keyAgreement.doPhase(publicKey, true);
//			SecretKey secretKey = keyAgreement.generateSecret(ALGORITHM);
////			Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm(), PROVIDER);
//			Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
//			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//			return cipher.doFinal(data);
//		} catch (Exception e) {
//			logger.warn(e.getLocalizedMessage(), e);
//			throw new EncryptEncodeException(e);
//		}
	}

	public static byte[] decode(PublicKey publicKey, PrivateKey privateKey, byte[] data) throws EncryptDecodeException {

		return data;
//		if (publicKey == null || privateKey == null) {
//			throw new NullPointerException();
//		}
//		try {
////			KeyAgreement keyAgreement = KeyAgreement.getInstance(KEY_ALGORITHM, PROVIDER);
//			KeyAgreement keyAgreement = KeyAgreement.getInstance(KEY_ALGORITHM);
//			keyAgreement.init(privateKey);
//			keyAgreement.doPhase(publicKey, true);
//			SecretKey secretKey = keyAgreement.generateSecret(ALGORITHM);
////			Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm(), PROVIDER);
//			Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
//			cipher.init(Cipher.DECRYPT_MODE, secretKey);
//			return cipher.doFinal(data);
//		} catch (Exception e) {
//			logger.warn(e.getLocalizedMessage(), e);
//			throw new EncryptDecodeException(e);
//		}
	}

	public static PublicKey getPublicKeyFromByteArray(byte[] bytes) throws EncryptEncodeException {
		try {
			// ObjectMapper objectMapper = new ObjectMapper();
			// DHPublicKeyObject dhPublicKeyObject2 = objectMapper.readValue(bytes,
			// DHPublicKeyObject.class);
			// DHPublicKeySpec dhPublicKeySpec = new DHPublicKeySpec(new
			// BigInteger(dhPublicKeyObject2.getY()),
			// new BigInteger(dhPublicKeyObject2.getP()), new
			// BigInteger(dhPublicKeyObject2.getG()));
			// KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, PROVIDER);
			// return keyFactory.generatePublic(dhPublicKeySpec);

//			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, PROVIDER);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			return keyFactory.generatePublic(new X509EncodedKeySpec(bytes));

		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage(), e);
			throw new EncryptEncodeException(e);
		}
	}

	public static byte[] publicKeyToByteArray(PublicKey publicKey) throws EncryptDecodeException {
		try {
			// ObjectMapper objectMapper = new ObjectMapper();
			// DHPublicKeyObject dhPublicKeyObject = new DHPublicKeyObject(dhPublicKey);
			// return objectMapper.writeValueAsBytes(dhPublicKeyObject);
			return publicKey.getEncoded();
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage(), e);
			throw new EncryptDecodeException(e);
		}
	}

	public static void main(String[] args) throws Exception {

		String data = "hello";
		System.out.println(data);
		KeyPair keyPair = getKeyPair();
		byte[] encode = encode(keyPair.getPublic(), keyPair.getPrivate(), data.getBytes());
		System.out.println(new String(encode));
		byte[] decode = decode(keyPair.getPublic(), keyPair.getPrivate(), encode);
		System.out.println(new String(decode));
		byte[] decode2 = decode(getPublicKeyFromByteArray(publicKeyToByteArray(keyPair.getPublic())),
				keyPair.getPrivate(), encode);
		System.out.println(new String(decode2));

	}
}
