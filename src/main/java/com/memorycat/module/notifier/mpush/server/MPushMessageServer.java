package com.memorycat.module.notifier.mpush.server;

import java.io.IOException;

import com.memorycat.module.notifier.mpush.exception.MPushMessageException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.server.config.ServerConfiguration;
import com.memorycat.module.notifier.mpush.server.model.LoginUser;

public interface MPushMessageServer {

	ServerConfiguration getServerConfiguration();

	MPushMessageModel sendMessage(LoginUser LoginUser, Object message)
			throws MPushMessageException, IOException, Exception;
}
