package com.memorycat.module.notifier.mpush.server.auth;

import com.memorycat.module.notifier.mpush.exception.auth.AuthenticationException;
import com.memorycat.module.notifier.mpush.exception.auth.PasswordErrorException;
import com.memorycat.module.notifier.mpush.exception.auth.UserNotFoundException;

public interface UserAuthenticator {

	ServerAuthenticatedResult login(Object info)
			throws UserNotFoundException, PasswordErrorException, AuthenticationException, Exception;
}
