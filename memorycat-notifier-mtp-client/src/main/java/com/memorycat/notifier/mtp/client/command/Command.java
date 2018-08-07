package com.memorycat.notifier.mtp.client.command;

import java.io.IOException;

import com.memorycat.notifier.mtp.client.ClientContext;
import com.memorycat.notifier.mtp.core.entity.MtpEntity;
import com.memorycat.notifier.mtp.core.exception.MtpEntityException;

public interface Command {

	void execute(ClientContext clientContext, MtpEntity requestMtpEntity)
			throws MtpEntityException, IOException, Exception;
}
