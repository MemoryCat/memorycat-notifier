package com.memorycat.module.notifier.mpush.client;

import java.io.IOException;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.module.notifier.mpush.exception.MPushMessageException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;

public class MPushMessageClientMessageDisptcher {

	private static final Logger logger = LoggerFactory.getLogger(MPushMessageClientMessageDisptcher.class);

	private final MPushMessageClient mPushMessageClient;

	public MPushMessageClientMessageDisptcher(MPushMessageClient mPushMessageClient) {
		super();
		this.mPushMessageClient = mPushMessageClient;
	}

	public void disptach(MPushMessageModel mPushMessageModel, IoSession context)
			throws MPushMessageException, IOException {
		logger.trace("dispatching message:" + mPushMessageModel);
		switch (mPushMessageModel.getMessageType()) {
		case AUTH_LOGIN_RESPONSE_OK:
				this.mPushMessageClient.getClientConfiguration().getLoginUser().setAuthentication(mPushMessageModel.getBody());
			break;

		default:
			break;
		}

	}

}
