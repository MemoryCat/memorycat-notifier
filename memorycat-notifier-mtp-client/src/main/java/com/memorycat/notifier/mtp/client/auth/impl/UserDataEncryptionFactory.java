package com.memorycat.notifier.mtp.client.auth.impl;

import com.memorycat.notifier.mtp.client.auth.UserDataEncryption;

public class UserDataEncryptionFactory {

	public static enum Type {
		DEFAULT, DH
	}

	public static UserDataEncryption create(Type type) {
		switch (type) {

		default:
			return new CommonDhUserDataEncryption();
		}
	}
}
