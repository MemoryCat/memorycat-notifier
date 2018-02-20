package com.memorycat.module.notifier.mpush.client.listener;

import com.memorycat.module.notifier.mpush.client.event.MPushMessageEvent;

public interface MPushMessageListener {

	void sendMessage(MPushMessageEvent event);

	void receveMessage(MPushMessageEvent event);
}
