package com.memorycat.notifier.mtp.client.command.impl;

import java.io.IOException;

import com.memorycat.notifier.mtp.client.ClientContext;
import com.memorycat.notifier.mtp.client.command.Command;
import com.memorycat.notifier.mtp.client.impl.MtpEntityHelper;
import com.memorycat.notifier.mtp.client.listener.FireListenerHelper;
import com.memorycat.notifier.mtp.core.entity.MtpEntity;
import com.memorycat.notifier.mtp.core.entity.message.NotificationMessage;
import com.memorycat.notifier.mtp.core.exception.MtpEntityException;
import com.memorycat.notifier.mtp.core.util.JsonUtil;

public class NotificationMessageRecvCommand implements Command {

	@Override
	public void execute(ClientContext clientContext, MtpEntity requestMtpEntity)
			throws MtpEntityException, IOException, Exception {
		NotificationMessage notificationMessage = JsonUtil.toObject(requestMtpEntity.getBody(),
				NotificationMessage.class);
		clientContext.getMtpClient()
				.sendMessage(MtpEntityHelper.notificationMessageRecv(clientContext, notificationMessage));
	
		FireListenerHelper.notifyNotificationMessageListener_recv(clientContext, requestMtpEntity, notificationMessage);
	}

}
