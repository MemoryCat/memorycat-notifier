package com.memorycat.notifier.mtp.client.command;

import com.memorycat.notifier.mtp.core.entity.MessageType;

public interface CommandFactory {

	Command getCommand(MessageType messageType);
}
