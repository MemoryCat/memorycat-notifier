package com.memorycat.module.notifier.mpush.server;

import java.util.LinkedList;
import java.util.List;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.module.notifier.mpush.auth.Authenticator;
import com.memorycat.module.notifier.mpush.event.LoginEvent;
import com.memorycat.module.notifier.mpush.exception.UnknownEventException;
import com.memorycat.module.notifier.mpush.listener.MPushMessageListener;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.model.MPushMessageType;

public class MPushMessageServerEventDisptcher {

	private static final Logger logger = LoggerFactory.getLogger(MPushMessageServerEventDisptcher.class);
	private final MPushMessageServer mPushMessageServer;
	private List<MPushMessageListener> listeners = new LinkedList<>();

	public MPushMessageServerEventDisptcher(MPushMessageServer mPushMessageServer) {
		super();
		this.mPushMessageServer = mPushMessageServer;
	}

	public void disptach(MPushMessageModel mPushMessageModel, IoSession context) {
		logger.trace("disptching:" + mPushMessageModel);

		if (this.listeners != null && this.listeners.size() > 0) {
			for (MPushMessageListener mPushMessageListener : listeners) {
				switch (mPushMessageModel.getMessageType()) {
				case AUTH_LOGIN_RESPONSE_RETRY:
					Authenticator authenticator = mPushMessageListener
							.login(new LoginEvent(mPushMessageModel, context));
					this.sendAuthention(context, authenticator);
					break;
				default:
					throw new UnknownEventException(mPushMessageModel);
				}
			}
		}
	}

	private void sendAuthention(IoSession context, Authenticator authenticator) {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.AUTH_ENCRYPT_REQUEST);
		Object login = authenticator.login();
		if (login == null) {
			throw new NullPointerException();
		}
		if (login instanceof byte[]) {
			mPushMessageModel.setBody((byte[]) login);
		} else if (login instanceof String) {
			mPushMessageModel.setBody(((String) login).getBytes());
		} else {
			mPushMessageModel.setBody(login.toString().getBytes());
		}
	};
}
