package com.memorycat.notifier.mtp.client.command.impl;

import java.io.IOException;

import com.memorycat.notifier.mtp.client.ClientContext;
import com.memorycat.notifier.mtp.client.command.Command;
import com.memorycat.notifier.mtp.client.impl.MtpEntityHelper;
import com.memorycat.notifier.mtp.client.listener.FireListenerHelper;
import com.memorycat.notifier.mtp.core.entity.MtpEntity;
import com.memorycat.notifier.mtp.core.exception.MtpEntityException;

public class HearBeatResponseCommand implements Command {

	@Override
	public void execute(ClientContext clientContext, MtpEntity requestMtpEntity)
			throws MtpEntityException, IOException, Exception {
		
		FireListenerHelper.notifyHeartBeatListener_heartBeatRequest(clientContext, requestMtpEntity);
		MtpEntity sendMessage = clientContext.getMtpClient()
				.sendMessage(MtpEntityHelper.heartBeatResponse(clientContext));
		FireListenerHelper.notifyHeartBeatListener_heatBeatResponse(clientContext, sendMessage);

	}

}
