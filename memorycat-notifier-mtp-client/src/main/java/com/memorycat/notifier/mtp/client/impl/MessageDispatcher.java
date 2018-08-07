package com.memorycat.notifier.mtp.client.impl;

import java.io.IOException;

import com.memorycat.notifier.mtp.core.entity.MtpEntity;
import com.memorycat.notifier.mtp.core.exception.MtpEntityException;

public interface MessageDispatcher {

	void dispathcer(MtpEntity mtpEntity) throws MtpEntityException, IOException, Exception;

}
