package com.memorycat.module.notifier.mpush.auth;

import com.memorycat.module.notifier.mpush.exception.auth.AuthenticationException;

public interface Authenticator {
	AuthenticatedResult login() throws AuthenticationException;
}
