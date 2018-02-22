package com.memorycat.module.notifier.mpush.client;

import java.io.IOException;
import java.util.List;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.module.notifier.mpush.client.event.MPushMessageEvent;
import com.memorycat.module.notifier.mpush.client.listener.MPushClientListener;
import com.memorycat.module.notifier.mpush.exception.MPushMessageException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.model.MPushMessageType;

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

		if (mPushMessageModel.getMessageType() == MPushMessageType.AUTH_ENCRYPT_RESPONSE_RETRY) {
			this.sendRequestEnctryptment();
			return;
		} else if (mPushMessageModel.getMessageType() == MPushMessageType.AUTH_ENCRYPT_RESPONSE) {
			byte[] body = mPushMessageModel.getBody();
			byte[] buf = new byte[body.length];
			System.arraycopy(body, 0, buf, 0, body.length);
			this.mPushMessageClient.getClientConfiguration().getClientUser().setServerKey(buf);
			this.mPushMessageClient
					.sendMessage(ClientMPushMessageHelper.login(this.mPushMessageClient.getClientConfiguration()));
			return;
		} else if (mPushMessageModel.getMessageType() == MPushMessageType.AUTH_LOGIN_RESPONSE_RETRY) {
			this.mPushMessageClient
					.sendMessage(ClientMPushMessageHelper.login(this.mPushMessageClient.getClientConfiguration()));
			return;
		} else if (mPushMessageModel.getMessageType() == MPushMessageType.AUTH_LOGIN_RESPONSE_OK) {
			List<MPushClientListener> listeners = this.mPushMessageClient.getClientConfiguration().getListeners();
			for (MPushClientListener mPushClientListener : listeners) {
				mPushClientListener
						.loginSuccessfully(new MPushMessageEvent(this.mPushMessageClient, mPushMessageModel));
			}
			return;
		} else if (mPushMessageModel.getMessageType() == MPushMessageType.AUTH_LOGIN_RESPONSE_NO) {
			List<MPushClientListener> listeners = this.mPushMessageClient.getClientConfiguration().getListeners();
			for (MPushClientListener mPushClientListener : listeners) {
				mPushClientListener
						.loginUnsuccessfully(new MPushMessageEvent(this.mPushMessageClient, mPushMessageModel));
			}
			return;
		} else {

			List<MPushClientListener> listeners = this.mPushMessageClient.getClientConfiguration().getListeners();
			for (MPushClientListener mPushClientListener : listeners) {
				mPushClientListener.receveMessage(new MPushMessageEvent(this.mPushMessageClient, mPushMessageModel));
			}
			return;
		}

	}

	private void sendRequestEnctryptment() throws MPushMessageException, IOException {
		this.mPushMessageClient
				.sendMessage(ClientMPushMessageHelper.requestEncrypt(this.mPushMessageClient.getClientConfiguration()));
	}

}
