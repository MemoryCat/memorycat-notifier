package com.memorycat.module.notifier.mpush.listener;

import com.memorycat.module.notifier.mpush.event.MPushEvent;

public interface AuthenticateListener {

	void login(MPushEvent event);

	void loginout(MPushEvent event);
}
