package com.memorycat.notifier.mtp.client.listener;

import com.memorycat.notifier.mtp.client.event.NotificationMessageEvent;

public interface NotificationMessageListener extends ClientListener {

	void recv(NotificationMessageEvent event);

	void read(NotificationMessageEvent event);
}
