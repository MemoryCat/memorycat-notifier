package com.memorycat.notifier.mtp.client.auth;

import com.memorycat.notifier.mtp.core.exception.AuthenticationException;

public interface Authenticator {

	byte[] auth() throws AuthenticationException;
}
