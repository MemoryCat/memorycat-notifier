package com.memorycat.notifier.mtp.client.listener;

import com.memorycat.notifier.mtp.client.event.MtpClientEvent;

public interface MtpClientListener extends ClientListener {

	void beforeConnect(MtpClientEvent event);

	void afterConnected(MtpClientEvent event);

	void beforeClose(MtpClientEvent event);
}
