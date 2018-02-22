package com.memorycat.module.notifier.mpush.server;

import java.util.Arrays;

import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.model.MPushMessageType;
import com.memorycat.module.notifier.mpush.server.config.ServerConfiguration;
import com.memorycat.module.notifier.mpush.server.model.LoginUser;

public class ServerMPushMessageHelper {

	public static MPushMessageModel retryEntrycptment(ServerConfiguration serverConfiguration, LoginUser loginUser) {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.AUTH_ENCRYPT_RESPONSE_RETRY);
		mPushMessageModel.setResponseSequence(serverConfiguration.getResponseSequence().incrementAndGet());
		mPushMessageModel.setBody(loginUser.getPublicKey().getEncoded());
		return mPushMessageModel;
	}

	public static MPushMessageModel responseEntrycptment(ServerConfiguration serverConfiguration, LoginUser loginUser) {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.AUTH_ENCRYPT_RESPONSE);
		mPushMessageModel.setResponseSequence(serverConfiguration.getResponseSequence().incrementAndGet());
		mPushMessageModel.setBody(loginUser.getPublicKey().getEncoded());
		mPushMessageModel.setBodyLenth((short) mPushMessageModel.getBody().length);
		System.out.println("###"+Arrays.toString(mPushMessageModel.getBody()));
		return mPushMessageModel;
	}

}
