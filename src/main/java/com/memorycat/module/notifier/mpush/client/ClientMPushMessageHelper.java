package com.memorycat.module.notifier.mpush.client;

import com.memorycat.module.notifier.db.exception.NotificationMessageSerializeException;
import com.memorycat.module.notifier.db.model.NotificationMessage;
import com.memorycat.module.notifier.db.util.JsonUtil;
import com.memorycat.module.notifier.mpush.client.config.ClientConfiguration;
import com.memorycat.module.notifier.mpush.exception.auth.AuthenticationException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.model.MPushMessageType;

public class ClientMPushMessageHelper {

	public static MPushMessageModel login(ClientConfiguration clientConfiguration) throws AuthenticationException {
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

	public static MPushMessageModel heartBeat(ClientConfiguration clientConfiguration) {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.STATE_HEARTBEAT_RESPONSE);
		mPushMessageModel.setRequestSequence(clientConfiguration.getRequestSequence().getAndIncrement());
		return mPushMessageModel;
	}

	public static MPushMessageModel recvDatabaseNotificationMessage(ClientConfiguration clientConfiguration,
			MPushMessageModel serverSendMPushMessageModel) throws NotificationMessageSerializeException {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.MESSAGE_DATABASE_CLIENT_RECV);
		mPushMessageModel.setResponseSequence(serverSendMPushMessageModel.getResponseSequence());
		mPushMessageModel.setRequestSequence(clientConfiguration.getRequestSequence().getAndIncrement());
		NotificationMessage notificationMessage = JsonUtil.fromJsonByteArray(serverSendMPushMessageModel.getBody());
		if (notificationMessage == null || notificationMessage.getId() == null) {
			throw new NotificationMessageSerializeException(mPushMessageModel, null);
		}
		mPushMessageModel.setBody(notificationMessage.getId().getBytes());
		return mPushMessageModel;
	}

	public static Object readDatabaseNotificationMessage(ClientConfiguration clientConfiguration,
			MPushMessageModel serverSendMPushMessageModel) throws NotificationMessageSerializeException {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.MESSAGE_DATABASE_CLIENT_READ);
		mPushMessageModel.setResponseSequence(serverSendMPushMessageModel.getResponseSequence());
		mPushMessageModel.setRequestSequence(clientConfiguration.getRequestSequence().getAndIncrement());
		NotificationMessage notificationMessage = JsonUtil.fromJsonByteArray(serverSendMPushMessageModel.getBody());
		if (notificationMessage == null || notificationMessage.getId() == null) {
			throw new NotificationMessageSerializeException(mPushMessageModel, null);
		}
		mPushMessageModel.setBody(notificationMessage.getId().getBytes());
		return mPushMessageModel;
	}
}
