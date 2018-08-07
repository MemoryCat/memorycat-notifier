package com.memorycat.notifier.mtp.client.command;

import java.util.EnumMap;
import java.util.Map;

import com.memorycat.notifier.mtp.client.command.impl.DefaultCommandFactory;
import com.memorycat.notifier.mtp.client.command.impl.EncryptResponseCommand;
import com.memorycat.notifier.mtp.client.command.impl.EncryptResponseRetryCommand;
import com.memorycat.notifier.mtp.client.command.impl.HearBeatResponseCommand;
import com.memorycat.notifier.mtp.client.command.impl.LoginSuccessfullyCommand;
import com.memorycat.notifier.mtp.client.command.impl.LoginUnsuccessfullyCommand;
import com.memorycat.notifier.mtp.client.command.impl.NotificationMessageRecvCommand;
import com.memorycat.notifier.mtp.core.entity.MessageType;

public class CommandFactoryBuilder {
	private final Map<MessageType, Command> commandsMap = new EnumMap<MessageType, Command>(MessageType.class);

	private CommandFactoryBuilder() {
	}

	public static CommandFactoryBuilder create() {
		return new CommandFactoryBuilder();
	}

	public static CommandFactoryBuilder createDefault() {
		CommandFactoryBuilder commandFactoryBuilder = new CommandFactoryBuilder();
		commandFactoryBuilder.add(MessageType.AUTH_ENCRYPT_RESPONSE, new EncryptResponseCommand());
		commandFactoryBuilder.add(MessageType.AUTH_ENCRYPT_RESPONSE_RETRY, new EncryptResponseRetryCommand());
		commandFactoryBuilder.add(MessageType.STATE_HEARTBEAT_REQUEST, new HearBeatResponseCommand());
		commandFactoryBuilder.add(MessageType.AUTH_LOGIN_RESPONSE_OK, new LoginSuccessfullyCommand());
		commandFactoryBuilder.add(MessageType.AUTH_LOGIN_RESPONSE_NO, new LoginUnsuccessfullyCommand());
		commandFactoryBuilder.add(MessageType.MESSAGE_NOTIFICATION_SERVER_SEND, new NotificationMessageRecvCommand());
		return commandFactoryBuilder;
	}

	public void add(MessageType messageType, Command command) {
		commandsMap.put(messageType, command);
	}

	public Command remove(MessageType messageType) {
		return commandsMap.remove(messageType);
	}

	public CommandFactory build() {
		DefaultCommandFactory defaultCommandFactory = new DefaultCommandFactory();
		defaultCommandFactory.getCommandsMap().clear();
		defaultCommandFactory.getCommandsMap().putAll(commandsMap);
		return defaultCommandFactory;
	}
}
