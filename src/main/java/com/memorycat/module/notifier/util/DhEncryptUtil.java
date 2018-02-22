package com.memorycat.module.notifier.util;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class DhEncryptUtil {

	private static final String DES = "des";
	private static final String DH = "dh";

	public static byte[] encode(byte[] publicKeyByteArray, PrivateKey privateKey, byte[] data)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {

		if (publicKeyByteArray == null) {
			throw new NullPointerException();
		}

		KeyFactory keyFactory = KeyFactory.getInstance(DH);
		PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyByteArray));
		KeyAgreement keyAgreement = KeyAgreement.getInstance(DH);
		keyAgreement.init(privateKey);
		keyAgreement.doPhase(publicKey, true);
		SecretKey secretKey = keyAgreement.generateSecret(DES);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(data);

		// return data;
	}

	public static byte[] decode(byte[] publicKeyByteArray, PrivateKey privateKey, byte[] data)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {

		if (publicKeyByteArray == null) {
			throw new NullPointerException();
		}

		KeyFactory keyFactory = KeyFactory.getInstance(DH);
		PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyByteArray));
		KeyAgreement keyAgreement = KeyAgreement.getInstance(DH);
		keyAgreement.init(privateKey);
		keyAgreement.doPhase(publicKey, true);
		SecretKey secretKey = keyAgreement.generateSecret(DES);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(data);

		// return data;

	}

	public static void main3(String[] args) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {

		KeyPair keyPair1 = KeyUtil.getKeyPair();
		KeyPair keyPair2 = KeyUtil.getKeyPair();

		byte[] helloBytes = "hello".getBytes();
		System.out.println(Arrays.toString(helloBytes));
		byte[] encode = DhEncryptUtil.encode(keyPair2.getPublic().getEncoded(), keyPair1.getPrivate(), helloBytes);
		System.out.println(Arrays.toString(encode));
		byte[] decode = DhEncryptUtil.decode(keyPair1.getPublic().getEncoded(), keyPair2.getPrivate(), encode);
		System.out.println(Arrays.toString(decode));

	}

	public static void main(String[] args) throws Exception {
		KeyPair keyPair1 = KeyUtil.getKeyPair();
		KeyPair keyPair2 = KeyUtil.getKeyPair();

		byte[] helloBytes = "hello".getBytes();
		System.out.println(Arrays.toString(helloBytes));
		byte[] encoded = keyPair2.getPublic().getEncoded();
		System.out.println(Arrays.toString(encoded));
		byte[] encode = DhEncryptUtil.encode(encoded, keyPair1.getPrivate(), helloBytes);
		System.out.println(Arrays.toString(encode));
		byte[] decode = DhEncryptUtil.decode(keyPair1.getPublic().getEncoded(), keyPair2.getPrivate(), encode);
		System.out.println(Arrays.toString(decode));

	}
}
