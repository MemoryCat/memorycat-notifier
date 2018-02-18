package com.memorycat.module.notifier.mpush.server.auth;

import com.memorycat.module.notifier.mpush.auth.AuthenticatedResult;
import com.memorycat.module.notifier.mpush.exception.auth.AuthenticationException;
import com.memorycat.module.notifier.mpush.exception.auth.PasswordErrorException;
import com.memorycat.module.notifier.mpush.exception.auth.UserNotFoundException;

public class UserAndPasswordAuthenticator implements UserAuthenticator {

	@Override
	public AuthenticatedResult login(Object info)
			throws UserNotFoundException, PasswordErrorException, AuthenticationException, Exception {
		if (info instanceof byte[]) {
			byte[] b = (byte[]) info;
			if ("asd".equals(new String(b))) {
				return new AuthenticatedResult() {
					@Override
					public byte[] toByteArray() {
						return "okkkk".getBytes();
					}
				};
			}

		}
		return null;
	}

}
