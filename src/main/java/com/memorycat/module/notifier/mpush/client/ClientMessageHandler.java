package com.memorycat.module.notifier.mpush.client;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.memorycat.module.notifier.mpush.exception.UnknownRecevicedMessageException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;

public class ClientMessageHandler implements IoHandler {

	private final MPushMessageClient mPushMessageClient;
	private final MPushMessageClientMessageDisptcher mPushMessageClientMessageDisptcher;

	public ClientMessageHandler(MPushMessageClient mPushMessageClient) {
		super();
		this.mPushMessageClient = mPushMessageClient;
		this.mPushMessageClientMessageDisptcher = new MPushMessageClientMessageDisptcher(mPushMessageClient);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		if (message instanceof MPushMessageModel) {
			MPushMessageModel mPushMessageModel = (MPushMessageModel) message;
			this.mPushMessageClientMessageDisptcher.disptach(mPushMessageModel, session);
		} else {
			throw new UnknownRecevicedMessageException();
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

}
