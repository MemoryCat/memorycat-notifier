package com.memorycat.notifier.test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Arrays;

import javax.crypto.Cipher;

public class KeyTest {

	public static void main(String[] args) {
		short a=Short.MAX_VALUE;
		System.out.println(a);
	}
	
	public static void main2(String[] args) throws Exception {
		
		
		
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("dh");
		keyPairGenerator.initialize(512);
		KeyPair keyPair1 = keyPairGenerator.generateKeyPair();
		KeyPair keyPair2 = keyPairGenerator.generateKeyPair();
		
		byte[] encoded = keyPair1.getPublic().getEncoded();
		System.out.println(encoded.length);
		System.out.println(Arrays.toString(encoded));
		
	
	}
}
