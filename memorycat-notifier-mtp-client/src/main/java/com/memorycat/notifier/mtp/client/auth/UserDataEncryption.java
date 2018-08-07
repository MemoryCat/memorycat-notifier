package com.memorycat.notifier.mtp.client.auth;

import com.memorycat.notifier.mtp.core.exception.DencryptException;
import com.memorycat.notifier.mtp.core.exception.EncryptException;

public interface UserDataEncryption {

	byte[] encrpt(byte[] data) throws EncryptException;

	byte[] decrypt(byte[] data) throws DencryptException;

	void setServerKey(Object key);

	void setClientKey(Object key);

	Object getServerKey();
}
