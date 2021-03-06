package com.memorycat.notifier.mtp.client.command.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.notifier.mtp.client.ClientContext;
import com.memorycat.notifier.mtp.client.command.Command;
import com.memorycat.notifier.mtp.client.listener.FireListenerHelper;
import com.memorycat.notifier.mtp.core.entity.MtpEntity;
import com.memorycat.notifier.mtp.core.exception.MtpEntityException;

public class LoginUnsuccessfullyCommand implements Command {
	private static final Logger logger = LoggerFactory.getLogger(LoginUnsuccessfullyCommand.class);

	@Override
	public void execute(ClientContext clientContext, MtpEntity requestMtpEntity)
			throws MtpEntityException, IOException, Exception {

		logger.info("登录失败");
		FireListenerHelper.notifyUserListener_afterLogin(clientContext, requestMtpEntity);
		
	}

}
