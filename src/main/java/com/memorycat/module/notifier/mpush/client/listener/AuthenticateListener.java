package com.memorycat.module.notifier.mpush.client.listener;

import com.memorycat.module.notifier.mpush.client.event.MPushMessageEvent;

public interface AuthenticateListener {

	void preLogin(MPushMessageEvent event);

	void postLogin(MPushMessageEvent event);

	void loginSuccessfully(MPushMessageEvent event);

	void loginUnsuccessfully(MPushMessageEvent event);

}
