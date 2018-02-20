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
			this.sendRequestEnctryptment(mPushMessageModel);
		} else if (mPushMessageModel.getMessageType() == MPushMessageType.AUTH_ENCRYPT_RESPONSE) {
			this.mPushMessageClient.getClientConfiguration().getClientUser().setServerKey(mPushMessageModel.getBody());
			this.mPushMessageClient
					.sendMessage(ClientMPushMessageHelper.login(this.mPushMessageClient.getClientConfiguration()));
		} else if (mPushMessageModel.getMessageType() == MPushMessageType.AUTH_LOGIN_RESPONSE_RETRY) {
			this.mPushMessageClient
					.sendMessage(ClientMPushMessageHelper.login(this.mPushMessageClient.getClientConfiguration()));
		} else if (mPushMessageModel.getMessageType() == MPushMessageType.AUTH_LOGIN_RESPONSE_OK) {
			List<MPushClientListener> listeners = this.mPushMessageClient.getClientConfiguration().getListeners();
			for (MPushClientListener mPushClientListener : listeners) {
				mPushClientListener
						.loginSuccessfully(new MPushMessageEvent(this.mPushMessageClient, mPushMessageModel));
			}
		} else if (mPushMessageModel.getMessageType() == MPushMessageType.AUTH_LOGIN_RESPONSE_NO) {
			List<MPushClientListener> listeners = this.mPushMessageClient.getClientConfiguration().getListeners();
			for (MPushClientListener mPushClientListener : listeners) {
				mPushClientListener
						.loginUnsuccessfully(new MPushMessageEvent(this.mPushMessageClient, mPushMessageModel));
			}
		} else {

			List<MPushClientListener> listeners = this.mPushMessageClient.getClientConfiguration().getListeners();
			for (MPushClientListener mPushClientListener : listeners) {
				mPushClientListener.receveMessage(new MPushMessageEvent(this.mPushMessageClient, mPushMessageModel));
			}

		}

	}

	private void sendRequestEnctryptment(MPushMessageModel serverMPushMessageModel)
			throws MPushMessageException, IOException {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.AUTH_ENCRYPT_REQUEST);
		mPushMessageModel.setRequestSequence(
				this.mPushMessageClient.getClientConfiguration().getRequestSequence().incrementAndGet());
		mPushMessageModel.setResponseSequence(serverMPushMessageModel.getRequestSequence());
		mPushMessageModel.setBody(this.mPushMessageClient.getClientConfiguration().getClientUser().getPublicKey());
		this.mPushMessageClient.sendMessage(mPushMessageModel);
	}

}
