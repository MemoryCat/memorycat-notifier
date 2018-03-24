package com.memorycat.module.notifier.mpush.server.auth;

import com.memorycat.module.notifier.mpush.auth.AuthenticatedResult;

public interface ServerAuthenticatedResult extends AuthenticatedResult {

	String getUserId();
}
