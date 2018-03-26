package com.memorycat.module.notifier.mpush.client;

import java.io.IOException;
import java.util.List;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.module.notifier.db.model.NotificationMessage;
import com.memorycat.module.notifier.db.util.JsonUtil;
import com.memorycat.module.notifier.mpush.client.config.ClientConfiguration;
import com.memorycat.module.notifier.mpush.client.event.MPushMessageEvent;
import com.memorycat.module.notifier.mpush.client.listener.MPushClientListener;
import com.memorycat.module.notifier.mpush.client.model.ClientUser;
import com.memorycat.module.notifier.mpush.exception.MPushMessageException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.model.MPushMessageType;
import com.memorycat.module.notifier.util.EncryptUtil;

public class MPushMessageClientMessageDisptcher {

	private static final Logger logger = LoggerFactory.getLogger(MPushMessageClientMessageDisptcher.class);

	private final MPushMessageClient mPushMessageClient;

	public MPushMessageClientMessageDisptcher(MPushMessageClient mPushMessageClient) {
		super();
		this.mPushMessageClient = mPushMessageClient;
	}

	public void disptach(MPushMessageModel mPushMessageModel, IoSession context)
			throws MPushMessageException, IOException, Exception {
		logger.trace("dispatching message:" + mPushMessageModel);

		// TODO listener未实现通知

		ClientConfiguration clientConfiguration = this.mPushMessageClient.getClientConfiguration();
		ClientUser clientUser = clientConfiguration.getClientUser();
		if (mPushMessageModel.getMessageType() == MPushMessageType.AUTH_ENCRYPT_RESPONSE) {
			clientUser.setServerKey(EncryptUtil.getPublicKeyFromByteArray(mPushMessageModel.getBody()));
			this.mPushMessageClient.sendMessage(ClientMPushMessageHelper.login(clientConfiguration));
			return;
		} else if (mPushMessageModel.getMessageType() == MPushMessageType.AUTH_ENCRYPT_RESPONSE_RETRY) {
			this.mPushMessageClient.sendMessage(ClientMPushMessageHelper.requestEncrypt(clientConfiguration));
			return;
		} else {

			// 先要解密原消息

			if (clientUser.getServerKey() == null) {
				logger.debug("丢弃消息，未与服务器进行密钥交换。" + mPushMessageModel);
				this.mPushMessageClient.sendMessage(ClientMPushMessageHelper.requestEncrypt(clientConfiguration));
				return;
			}

			byte[] body = mPushMessageModel.getBody();
			byte[] decode = EncryptUtil.decode(clientUser.getServerKey(), clientUser.getPrivateKey(), body);
			mPushMessageModel.setBody(decode);
			mPushMessageModel.setBodyLenth((short) decode.length);
			// 消息已解密完成。。。

			if (mPushMessageModel.getMessageType() == MPushMessageType.AUTH_LOGIN_RESPONSE_RETRY) {
				this.mPushMessageClient.sendMessage(ClientMPushMessageHelper.login(clientConfiguration));
				return;
			} else if (mPushMessageModel.getMessageType() == MPushMessageType.AUTH_LOGIN_RESPONSE_OK) {
				List<MPushClientListener> listeners = clientConfiguration.getListeners();
				for (MPushClientListener mPushClientListener : listeners) {
					mPushClientListener
							.loginSuccessfully(new MPushMessageEvent(this.mPushMessageClient, mPushMessageModel));
				}
				return;
			} else if (mPushMessageModel.getMessageType() == MPushMessageType.AUTH_LOGIN_RESPONSE_NO) {
				List<MPushClientListener> listeners = clientConfiguration.getListeners();
				for (MPushClientListener mPushClientListener : listeners) {
					mPushClientListener
							.loginUnsuccessfully(new MPushMessageEvent(this.mPushMessageClient, mPushMessageModel));
				}
				return;
			} else if (mPushMessageModel.getMessageType() == MPushMessageType.STATE_HEARTBEAT_REQUEST) {
				// TODO
				logger.trace("client try heart beat ...");
				this.mPushMessageClient.sendMessage(
						ClientMPushMessageHelper.heartBeat(this.mPushMessageClient.getClientConfiguration()));
				return;
			} else if (mPushMessageModel.getMessageType() == MPushMessageType.MESSAGE_DATABASE_SERVER_SEND) {
				NotificationMessage notificationMessage = JsonUtil.fromJsonByteArray(mPushMessageModel.getBody());
				logger.debug("recv db msg :" + notificationMessage);
				this.mPushMessageClient.sendMessage(ClientMPushMessageHelper.recvDatabaseNotificationMessage(
						this.mPushMessageClient.getClientConfiguration(), mPushMessageModel));
				
				
				this.mPushMessageClient.sendMessage(ClientMPushMessageHelper.readDatabaseNotificationMessage(
						this.mPushMessageClient.getClientConfiguration(), mPushMessageModel));
				
			} else {

				logger.trace("未知消息：" + new String(mPushMessageModel.getBody()));
				List<MPushClientListener> listeners = clientConfiguration.getListeners();
				for (MPushClientListener mPushClientListener : listeners) {
					mPushClientListener
							.receveMessage(new MPushMessageEvent(this.mPushMessageClient, mPushMessageModel));
				}
				return;
			}

		}

	}

}
