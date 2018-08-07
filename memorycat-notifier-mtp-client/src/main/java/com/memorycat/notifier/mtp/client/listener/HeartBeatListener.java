package com.memorycat.notifier.mtp.client.listener;

import com.memorycat.notifier.mtp.client.event.HeartBeatEvent;

public interface HeartBeatListener extends ClientListener {

	void heartBeatRequest(HeartBeatEvent event);

	void heatBeatResponse(HeartBeatEvent event);
}
