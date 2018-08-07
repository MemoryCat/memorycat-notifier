package com.memorycat.notifier.mtp.client.command.impl;

import java.io.IOException;

import com.memorycat.notifier.mtp.client.ClientContext;
import com.memorycat.notifier.mtp.client.command.Command;
import com.memorycat.notifier.mtp.client.impl.MtpEntityHelper;
import com.memorycat.notifier.mtp.client.listener.FireListenerHelper;
import com.memorycat.notifier.mtp.core.entity.MtpEntity;
import com.memorycat.notifier.mtp.core.exception.MtpEntityException;

public class EncryptResponseRetryCommand implements Command {

	@Override
	public void execute(ClientContext clientContext, MtpEntity requestMtpEntity)
			throws MtpEntityException, IOException, Exception {
		
		FireListenerHelper.notifyUserListener_beforeEncrypt(clientContext, requestMtpEntity);
		clientContext.getMtpClient().sendMessage(MtpEntityHelper.encryptRequest(clientContext));

	}

}
