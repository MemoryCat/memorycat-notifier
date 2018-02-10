package com.memorycat.module.notifier.mpush.auth;

import com.memorycat.module.notifier.mpush.exception.AuthenticationException;

public interface Authenticator {
	Object login() throws AuthenticationException;
}
