package com.memorycat.module.notifier.mpush.client;

import com.memorycat.module.notifier.mpush.client.config.ClientConfiguration;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.model.MPushMessageType;

public class ClientMPushMessageHelper {

	public static MPushMessageModel login(ClientConfiguration clientConfiguration) {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.AUTH_LOGIN_REQUEST);
		mPushMessageModel.setRequestSequence(clientConfiguration.getRequestSequence().getAndIncrement());
		mPushMessageModel.setBody(clientConfiguration.getClientUser().getAuthenticator().login().toByteArray());
		return mPushMessageModel;
	}

	public static MPushMessageModel requestEncrypt(ClientConfiguration clientConfiguration) {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.AUTH_ENCRYPT_REQUEST);
		mPushMessageModel.setRequestSequence(clientConfiguration.getRequestSequence().getAndIncrement());
		mPushMessageModel.setBody(clientConfiguration.getClientUser().getPublicKey().getEncoded());
		return mPushMessageModel;
	}
}
