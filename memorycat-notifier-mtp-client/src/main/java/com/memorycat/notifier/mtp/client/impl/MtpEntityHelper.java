package com.memorycat.notifier.mtp.client.impl;

import java.security.KeyPair;

import javax.crypto.interfaces.DHPublicKey;

import com.memorycat.notifier.mtp.client.ClientContext;
import com.memorycat.notifier.mtp.client.auth.User;
import com.memorycat.notifier.mtp.client.auth.impl.UserDataEncryptionFactory;
import com.memorycat.notifier.mtp.client.auth.impl.UserDataEncryptionFactory.Type;
import com.memorycat.notifier.mtp.core.entity.MessageType;
import com.memorycat.notifier.mtp.core.entity.MtpEntity;
import com.memorycat.notifier.mtp.core.entity.auth.EncryptionDto;
import com.memorycat.notifier.mtp.core.entity.auth.EncryptionType;
import com.memorycat.notifier.mtp.core.entity.message.NotificationMessage;
import com.memorycat.notifier.mtp.core.exception.JsonException;
import com.memorycat.notifier.mtp.core.exception.MtpEncryptionException;
import com.memorycat.notifier.mtp.core.util.Base64Util;
import com.memorycat.notifier.mtp.core.util.JsonUtil;
import com.memorycat.notifier.mtp.core.util.KeyUtil;

public class MtpEntityHelper {

	public static MtpEntity encryptRequest(ClientContext clientContext) throws JsonException, MtpEncryptionException {
		KeyPair keyPair = KeyUtil.generateKeyPair();
		User user = clientContext.getUser();
		user.setUserDataEncryption(UserDataEncryptionFactory.create(Type.DEFAULT));
		user.getUserDataEncryption().setClientKey(keyPair);
		DHPublicKey dhPublicKey = (DHPublicKey) keyPair.getPublic();

		MtpEntity mtpEntity = new MtpEntity();
		mtpEntity.setMessageType(MessageType.AUTH_ENCRYPT_REQUEST);

		EncryptionDto encryptionDto = new EncryptionDto();
		encryptionDto.setEncryptionType(EncryptionType.DH);
		encryptionDto.getData().put("y", dhPublicKey.getY().toString());
		encryptionDto.getData().put("p", dhPublicKey.getParams().getP().toString());
		encryptionDto.getData().put("g", dhPublicKey.getParams().getG().toString());
		mtpEntity.setBody(Base64Util.encode(JsonUtil.toByteArray(encryptionDto)));
		return mtpEntity;
	}

	public static MtpEntity loginRequest(byte[] data) {
		MtpEntity mtpEntity = new MtpEntity();
		mtpEntity.setMessageType(MessageType.AUTH_LOGIN_REQUEST);
		mtpEntity.setBody(data);
		return mtpEntity;
	}

	public static MtpEntity heartBeatResponse(ClientContext clientContext) {
		MtpEntity mtpEntity = new MtpEntity();
		mtpEntity.setMessageType(MessageType.STATE_HEARTBEAT_RESPONSE);
		return mtpEntity;
	}

	public static MtpEntity notificationMessageRecv(ClientContext clientContext,
			NotificationMessage notificationMessage) {
		MtpEntity mtpEntity = new MtpEntity();
		mtpEntity.setMessageType(MessageType.MESSAGE_NOTIFICATION_CLIENT_RECV);
		mtpEntity.setBody(notificationMessage.getId().getBytes());
		return mtpEntity;
	}

}
