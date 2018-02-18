package com.memorycat.module.notifier.mpush.client;

import java.io.IOException;

import com.memorycat.module.notifier.mpush.client.config.ClientConfiguration;
import com.memorycat.module.notifier.mpush.exception.MPushMessageException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;

public interface MPushMessageClient {

	MPushMessageModel sendMessage(Object message) throws MPushMessageException, IOException;

	ClientConfiguration getClientConfiguration();

}
