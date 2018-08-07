package com.memorycat.notifier.mtp.client.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.notifier.mtp.client.ClientContext;
import com.memorycat.notifier.mtp.client.ConnectionConfig;
import com.memorycat.notifier.mtp.client.MtpClient;
import com.memorycat.notifier.mtp.client.listener.FireListenerHelper;
import com.memorycat.notifier.mtp.core.entity.MessageType;
import com.memorycat.notifier.mtp.core.entity.MtpEntity;
import com.memorycat.notifier.mtp.core.entity.SendFrom;
import com.memorycat.notifier.mtp.core.exception.MemoryCatNotifierException;
import com.memorycat.notifier.mtp.core.exception.MtpEntityException;
import com.memorycat.notifier.mtp.core.exception.UnknownPreparedSendMtpEntityException;

public class DefaultMtpClient implements MtpClient {

	private static final Logger logger = LoggerFactory.getLogger(DefaultMtpClient.class);
	private final ClientContext clientContext;
	private IoSession ioSession;

	public DefaultMtpClient(ClientContext clientContext) throws MemoryCatNotifierException {
		super();
		this.clientContext = clientContext;
		this.clientContext.setMtpClient(this);
		this.init();
	}

	private void init() throws MemoryCatNotifierException {
		this.clientContext.getConnectionConfig().getIoConnector().setHandler(new MtpIoHandler(this.clientContext));

		ConnectionConfig connectionConfig = this.getClientContext().getConnectionConfig();

		logger.info("connecting.........");
		FireListenerHelper.notifyMtpClientListener_beforeConnect(clientContext);
		try {
			ConnectFuture connectFuture = connectionConfig.getIoConnector()
					.connect(new InetSocketAddress(connectionConfig.getHostAddress(), connectionConfig.getPort()));
			connectFuture.await();
			this.ioSession = connectFuture.getSession();

			logger.info("connected !");
			FireListenerHelper.notifyMtpClientListener_afterConnected(clientContext);

		} catch (Exception e) {
			logger.info("connect failed !");
			FireListenerHelper.notifyMtpClientListener_afterConnected(clientContext);
			logger.warn(e.getLocalizedMessage(), e);
			throw new MemoryCatNotifierException(e);
		}
	}

	@Override
	public ClientContext getClientContext() {
		return this.clientContext;
	}

	@Override
	public MtpEntity sendMessage(Object message) throws MtpEntityException, IOException, Exception {

		MtpEntity mtpEntity = null;
		if (message instanceof MtpEntity) {
			mtpEntity = (MtpEntity) message;
		} else {
			mtpEntity = new MtpEntity();
			if (message instanceof String) {
				mtpEntity.setBody(((String) message).getBytes());
			} else if (message instanceof byte[]) {
				mtpEntity.setBody((byte[]) message);
			} else if (message instanceof Serializable) {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
				objectOutputStream.writeObject((Serializable) message);
				objectOutputStream.close();
				mtpEntity.setBody(byteArrayOutputStream.toByteArray());
			} else {
				throw new UnknownPreparedSendMtpEntityException((String) message);
			}
		}
		mtpEntity.setSendFrom(SendFrom.CLIENT);
		if (mtpEntity.getMessageType() == MessageType.UNKOWN) {
			mtpEntity.setMessageType(MessageType.MESSAGE_COMMON);
		}
		mtpEntity.setBodyLenth(mtpEntity.getBody().length);
		if(logger.isTraceEnabled()) {
			logger.trace("sending: " + message + "\n" + new String(mtpEntity.getBody()));
		}
		FireListenerHelper.notifyMtpEntityMessageListener_send(clientContext, mtpEntity);
		this.ioSession.write(mtpEntity);
		return mtpEntity;
	}

	@Override
	public void close() throws IOException {
		FireListenerHelper.notifyMtpClientListener_beforeClose(clientContext);
		this.clientContext.getConnectionConfig().getIoConnector().dispose();
	}

	@Override
	public MtpEntity startEncrypt() throws MtpEntityException, IOException, Exception {
		MtpEntity mtpEntity = MtpEntityHelper.encryptRequest(this.clientContext);
		logger.trace("startEncrypt:" + mtpEntity);
		FireListenerHelper.notifyUserListener_beforeEncrypt(clientContext, mtpEntity);
		return this.sendMessage(mtpEntity);
	}
}
