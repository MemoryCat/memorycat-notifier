package com.memorycat.module.notifier.util;

public class ByteUtil {

	public static byte[] shortToByteArray(short s) {
		int temp = s;
		byte[] b = new byte[2];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Integer(temp & 0xff).byteValue();// 将最低位保存在最低位
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}

	public static byte[] longToByteArray(long l) {
		long temp = l;
		byte[] b = new byte[8];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Long(temp & 0xff).byteValue();// 将最低位保存在最低位
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}

	public static short byteArrayToShort(byte[] ba, int start) {
		short s = 0;
		short s0 = (short) (ba[start] & 0xff);// 最低位
		short s1 = (short) (ba[start + 1] & 0xff);
		s1 <<= 8;
		s = (short) (s0 | s1);
		return s;
	}

	public static long byteArrayToLong(byte[] ba, int start) {

		long s = 0;
		long s0 = ba[start + 0] & 0xff;// 最低位
		long s1 = ba[start + 1] & 0xff;
		long s2 = ba[start + 2] & 0xff;
		long s3 = ba[start + 3] & 0xff;
		long s4 = ba[start + 4] & 0xff;// 最低位
		long s5 = ba[start + 5] & 0xff;
		long s6 = ba[start + 6] & 0xff;
		long s7 = ba[start + 7] & 0xff;

		// s0不变
		s1 <<= 8;
		s2 <<= 16;
		s3 <<= 24;
		s4 <<= 8 * 4;
		s5 <<= 8 * 5;
		s6 <<= 8 * 6;
		s7 <<= 8 * 7;
		s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
		return s;
	}

	public static boolean equal(byte[] a, byte[] b) {
		if (a == null && b == null) {
			return true;
		}
		if (a.length == b.length) {
			for (int i = 0; i < a.length; i++) {
				if (a[i] != b[i]) {
					return false;
				}
			}
			return true;
		}

		return false;
	}
}
