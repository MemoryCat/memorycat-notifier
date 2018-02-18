package com.memorycat.module.notifier.mpush.client;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.module.notifier.mpush.auth.AuthenticatedResult;
import com.memorycat.module.notifier.mpush.auth.Authenticator;
import com.memorycat.module.notifier.mpush.client.config.ClientConfiguration;
import com.memorycat.module.notifier.mpush.exception.MPushMessageException;
import com.memorycat.module.notifier.mpush.exception.UnknownPreparedSendMessageException;
import com.memorycat.module.notifier.mpush.exception.auth.AuthenticationException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.model.MPushMessageType;
import com.memorycat.module.notifier.mpush.protocol.MPushMessageProtocolFactory;

public class MPushMessageClientImpl implements MPushMessageClient {
	private static final Logger logger = LoggerFactory.getLogger(MPushMessageClientImpl.class);
	private final NioDatagramConnector nioDatagramConnector = new NioDatagramConnector();
	private final ClientConfiguration clientConfiguration;

	public MPushMessageClientImpl(ClientConfiguration clientConfiguration) {
		super();
		if (clientConfiguration == null) {
			throw new NullPointerException();
		}
		this.clientConfiguration = clientConfiguration;

		nioDatagramConnector.getSessionConfig().setReuseAddress(true);
		nioDatagramConnector.getFilterChain().addFirst("log", new LoggingFilter());
		nioDatagramConnector.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new MPushMessageProtocolFactory()));
		nioDatagramConnector.setHandler(new ClientMessageHandler(this));
		ConnectFuture connectFuture = nioDatagramConnector.connect(new InetSocketAddress("localhost", 12345));
		try {
			connectFuture.await();
		} catch (InterruptedException e) {
			logger.warn(e.getLocalizedMessage(), e);
		}
		this.getClientConfiguration().setIoSession(connectFuture.getSession());
		logger.debug("client is running");
	}

	private void login() throws MPushMessageException, IOException {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.AUTH_LOGIN_REQUEST);
		mPushMessageModel.setBody(this.getClientConfiguration().getAuthenticator().login().toByteArray());
		mPushMessageModel.setRequestSequence(this.getClientConfiguration().getRequestSequence().getAndIncrement());
		mPushMessageModel.setResponseSequence(0);
		mPushMessageModel.setBodyLenth((short) mPushMessageModel.getBody().length);
		this.sendMessage(mPushMessageModel);
	}

	@Override
	public MPushMessageModel sendMessage(Object message) throws MPushMessageException, IOException {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		if (message instanceof MPushMessageModel) {
			mPushMessageModel = (MPushMessageModel) message;
		} else if (message instanceof String) {
			mPushMessageModel.setBody(((String) message).getBytes());
		} else if (message instanceof byte[]) {
			mPushMessageModel.setBody((byte[]) message);
		} else {
			throw new UnknownPreparedSendMessageException(this.getClientConfiguration().getLoginUser(), message);
		}
		this.getClientConfiguration().getIoSession().write(mPushMessageModel);
		return mPushMessageModel;
	}

	@Override
	public ClientConfiguration getClientConfiguration() {
		return this.clientConfiguration;
	}

	public static void main(String[] args) throws MPushMessageException, IOException {
		ClientConfiguration clientConfiguration = new ClientConfiguration();
		clientConfiguration.setAuthenticator(new Authenticator() {
			@Override
			public AuthenticatedResult login() throws AuthenticationException {
				return new AuthenticatedResult() {
					@Override
					public byte[] toByteArray() {
						return "asd".getBytes();
					}
				};
			}
		});
		MPushMessageClientImpl mPushMessageClientImpl = new MPushMessageClientImpl(clientConfiguration);
		clientConfiguration.setmPushMessageClient(mPushMessageClientImpl);
		mPushMessageClientImpl.login();
	}
}
