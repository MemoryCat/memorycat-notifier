package com.memorycat.module.notifier.mpush.server;

import java.io.IOException;

import com.memorycat.module.notifier.mpush.exception.MPushMessageException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;

public interface MPushMessageServer {

	MPushMessageModel sendMessage(Object context, Object message) throws MPushMessageException, IOException;
}
