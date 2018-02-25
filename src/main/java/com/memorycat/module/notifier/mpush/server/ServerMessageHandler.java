package com.memorycat.module.notifier.mpush.server;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.module.notifier.mpush.exception.UnknownRecevicedMessageException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;

public class ServerMessageHandler implements IoHandler {

	private static final Logger logger = LoggerFactory.getLogger(ServerMessageHandler.class);
	private final MPushMessageServer mPushMessageServer;
	private final MPushMessageServerMessageDisptcher mPushMessageEventDisptcher;

	public ServerMessageHandler(MPushMessageServer mPushMessageServer) {
		super();
		this.mPushMessageServer = mPushMessageServer;
		this.mPushMessageEventDisptcher = new MPushMessageServerMessageDisptcher(this.mPushMessageServer);
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
		logger.warn("【session】" + session + ",【exception】" + cause.getMessage(), cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		if (message instanceof MPushMessageModel) {
			MPushMessageModel mPushMessageModel = (MPushMessageModel) message;
			this.mPushMessageEventDisptcher.disptach(mPushMessageModel, session);
		} else {
			throw new UnknownRecevicedMessageException();
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {

	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

}
