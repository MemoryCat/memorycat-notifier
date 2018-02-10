package com.memorycat.module.notifier.mpush.listener;

import com.memorycat.module.notifier.mpush.auth.Authenticator;
import com.memorycat.module.notifier.mpush.event.MPushEvent;

public interface AuthenticateListener {

	Authenticator login(MPushEvent event);

	void loginout(MPushEvent event);
}
