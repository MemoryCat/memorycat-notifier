package com.memorycat.module.notifier.mpush.client;

import com.memorycat.module.notifier.mpush.client.config.ClientConfiguration;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.model.MPushMessageType;

public class ClientMPushMessageHelper {

	public static MPushMessageModel login(ClientConfiguration clientConfiguration) {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.AUTH_LOGIN_REQUEST);
		mPushMessageModel.setBody(clientConfiguration.getClientUser().getAuthenticator().login().toByteArray());
		mPushMessageModel.setRequestSequence(clientConfiguration.getRequestSequence().getAndIncrement());
		mPushMessageModel.setResponseSequence(0);
		mPushMessageModel.setBodyLenth((short) mPushMessageModel.getBody().length);
		return mPushMessageModel;
	}
}
