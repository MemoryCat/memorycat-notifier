package com.memorycat.notifier.mtp.client.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.notifier.mtp.client.ClientContext;
import com.memorycat.notifier.mtp.client.command.Command;
import com.memorycat.notifier.mtp.client.listener.FireListenerHelper;
import com.memorycat.notifier.mtp.core.entity.MessageType;
import com.memorycat.notifier.mtp.core.entity.MtpEntity;
import com.memorycat.notifier.mtp.core.exception.MtpEntityException;

public class DefaultMessageDispatcher implements MessageDispatcher {

	private static final Logger logger = LoggerFactory.getLogger(DefaultMessageDispatcher.class);
	private final ClientContext clientContext;

	public DefaultMessageDispatcher(ClientContext clientContext) {
		super();
		this.clientContext = clientContext;
	}

	@Override
	public void dispathcer(MtpEntity mtpEntity) throws MtpEntityException, IOException, Exception {

		FireListenerHelper.notifyMtpEntityMessageListener_recv(clientContext, mtpEntity);
		
		MessageType messageType = mtpEntity.getMessageType();
		Command command = this.clientContext.getCommandFactory().getCommand(messageType);

		// TODO 先要判断是否已登录之类的

		if (command != null) {
			command.execute(this.clientContext, mtpEntity);
		} else {
			logger.info("could not found command for : " + mtpEntity);
			FireListenerHelper.notifyMtpEntityMessageListener_drop(clientContext, mtpEntity);
		}
	}

}
