package com.memorycat.notifier.mtp.client.listener;

import com.memorycat.notifier.mtp.client.event.UserEvent;

public interface UserListener extends ClientListener {

	void beforeEncrypt(UserEvent event);

	void afterEncrypt(UserEvent event);

	void beforeLogin(UserEvent event);

	void afterLogin(UserEvent event);
}
