package com.memorycat.module.notifier;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		BigDecimal bigDecimal = new BigDecimal("123456789012345678901111111111111111111111111111");
		BigInteger bigInteger = bigDecimal.toBigInteger();
		System.out.println(bigInteger.toString());
		System.out.println("Hello World!");
	}
}
