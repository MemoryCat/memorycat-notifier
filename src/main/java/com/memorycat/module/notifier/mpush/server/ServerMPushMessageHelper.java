package com.memorycat.module.notifier.mpush.server;

import com.memorycat.module.notifier.db.model.NotificationMessage;
import com.memorycat.module.notifier.db.util.JsonUtil;
import com.memorycat.module.notifier.exception.EncryptDecodeException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.model.MPushMessageType;
import com.memorycat.module.notifier.mpush.server.config.ServerConfiguration;
import com.memorycat.module.notifier.mpush.server.model.LoginUser;
import com.memorycat.module.notifier.util.EncryptUtil;

public class ServerMPushMessageHelper {

	public static MPushMessageModel retryEntrycptment(ServerConfiguration serverConfiguration, LoginUser loginUser)
			throws EncryptDecodeException {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.AUTH_ENCRYPT_RESPONSE_RETRY);
		mPushMessageModel.setResponseSequence(serverConfiguration.getResponseSequence().incrementAndGet());
		mPushMessageModel.setBody(EncryptUtil.publicKeyToByteArray(loginUser.getPublicKey()));
		loginUser.setClientKey(null);
		return mPushMessageModel;
	}

	public static MPushMessageModel responseEntrycptment(ServerConfiguration serverConfiguration, LoginUser loginUser)
			throws EncryptDecodeException {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.AUTH_ENCRYPT_RESPONSE);
		mPushMessageModel.setResponseSequence(serverConfiguration.getResponseSequence().incrementAndGet());
		mPushMessageModel.setBody(EncryptUtil.publicKeyToByteArray(loginUser.getPublicKey()));
		mPushMessageModel.setBodyLenth((short) mPushMessageModel.getBody().length);
		return mPushMessageModel;
	}

	public static MPushMessageModel heartBeat(ServerConfiguration serverConfiguration) {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.STATE_HEARTBEAT_REQUEST);
		mPushMessageModel.setResponseSequence(serverConfiguration.getResponseSequence().incrementAndGet());
		return mPushMessageModel;
	}

	public static MPushMessageModel dbNotificationMessage(ServerConfiguration serverConfiguration,
			NotificationMessage notificationMessage) {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.MESSAGE_DATABASE_SERVER_SEND);
		// mPushMessageModel.setBody(
		// notificationMessage.getContent() != null ?
		// notificationMessage.getContent().getBytes() : new byte[] {});
		mPushMessageModel.setBody(JsonUtil.toString(notificationMessage).getBytes());
		return mPushMessageModel;
	}

}
