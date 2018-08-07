package com.memorycat.notifier.mtp.client.impl;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.notifier.mtp.client.ClientContext;
import com.memorycat.notifier.mtp.core.entity.MtpEntity;

public class MtpIoHandler implements IoHandler {

	private static final Logger logger = LoggerFactory.getLogger(MtpIoHandler.class);
	private final ClientContext clientContext;
	private final MessageDispatcher messageDispatcher;

	public MtpIoHandler(ClientContext clientContext) {
		super();
		this.clientContext = clientContext;
		this.messageDispatcher = new DefaultMessageDispatcher(this.clientContext);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.warn(cause.getLocalizedMessage(), cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {

		if (!(message instanceof MtpEntity)) {
			throw new IllegalArgumentException("message is not instanceof MtpEntity");
		}
		MtpEntity mtpEntity = (MtpEntity) message;
		logger.debug("recv: " + message.toString() + "\n" + new String());
		this.messageDispatcher.dispathcer(mtpEntity);
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
