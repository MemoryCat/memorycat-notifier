package com.memorycat.notifier.mtp.client.command.impl;

import java.util.EnumMap;
import java.util.Map;

import com.memorycat.notifier.mtp.client.command.Command;
import com.memorycat.notifier.mtp.client.command.CommandFactory;
import com.memorycat.notifier.mtp.core.entity.MessageType;

public class DefaultCommandFactory implements CommandFactory {

	private final Map<MessageType, Command> commandsMap = new EnumMap<MessageType, Command>(MessageType.class);

	@Override
	public Command getCommand(MessageType messageType) {
		return commandsMap.get(messageType);
	}

	public Map<MessageType, Command> getCommandsMap() {
		return commandsMap;
	}

}
