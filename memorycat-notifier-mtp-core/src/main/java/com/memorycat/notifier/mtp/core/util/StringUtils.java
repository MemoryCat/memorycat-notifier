package com.memorycat.notifier.mtp.core.util;

public class StringUtils {

	/**
	 * 判断是不是相等，null也等于""
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean nullEquals(String a, String b) {
		if (a == null && b == null) {
			return true;
		} else if (a != null && a.equals(b)) {
			return true;
		} else if (b != null && b.equals(a)) {
			return true;
		} else if (a == null && "".equals(b)) {
			return true;
		} else if (b == null && "".equals(a)) {
		}
		return false;
	}

	/**
	 * <p>
	 * Checks if a CharSequence is empty (""), null or whitespace only.
	 * </p>
	 *
	 * <p>
	 * Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank("")        = true
	 * StringUtils.isBlank(" ")       = true
	 * StringUtils.isBlank("bob")     = false
	 * StringUtils.isBlank("  bob  ") = false
	 * </pre>
	 *
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is null, empty or whitespace only
	 * @since 2.0
	 * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
	 */
	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if a CharSequence is not empty (""), not null and not whitespace only.
	 * </p>
	 *
	 * <p>
	 * Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isNotBlank(null)      = false
	 * StringUtils.isNotBlank("")        = false
	 * StringUtils.isNotBlank(" ")       = false
	 * StringUtils.isNotBlank("bob")     = true
	 * StringUtils.isNotBlank("  bob  ") = true
	 * </pre>
	 *
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is not empty and not null and not
	 *         whitespace only
	 * @since 2.0
	 * @since 3.0 Changed signature from isNotBlank(String) to
	 *        isNotBlank(CharSequence)
	 */
	public static boolean isNotBlank(final CharSequence cs) {
		return !isBlank(cs);
	}
}
