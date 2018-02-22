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
import com.memorycat.module.notifier.mpush.client.protocol.MPushMessageClientProtocolFactory;
import com.memorycat.module.notifier.mpush.exception.MPushMessageException;
import com.memorycat.module.notifier.mpush.exception.UnknownPreparedSendMessageException;
import com.memorycat.module.notifier.mpush.exception.auth.AuthenticationException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.model.MPushMessageType;

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
				new ProtocolCodecFilter(new MPushMessageClientProtocolFactory(this.clientConfiguration)));
		nioDatagramConnector.setHandler(new ClientMessageHandler(this));
		ConnectFuture connectFuture = nioDatagramConnector.connect(
				new InetSocketAddress(this.clientConfiguration.getServerHost(), this.clientConfiguration.getPort()));
		try {
			connectFuture.await();
		} catch (InterruptedException e) {
			logger.warn(e.getLocalizedMessage(), e);
		}
		this.getClientConfiguration().setIoSession(connectFuture.getSession());
		logger.debug("client is running");
	}

	@Override
	public MPushMessageModel sendMessage(Object message) throws MPushMessageException, IOException {
		MPushMessageModel mPushMessageModel = null;
		if (message instanceof MPushMessageModel) {
			mPushMessageModel = (MPushMessageModel) message;
		} else {
			mPushMessageModel = new MPushMessageModel();
			mPushMessageModel.setMessageType(MPushMessageType.COMMON_MESSAGE);
			if (message instanceof String) {
				mPushMessageModel.setBody(((String) message).getBytes());
			} else if (message instanceof byte[]) {
				mPushMessageModel.setBody((byte[]) message);
			} else {
				throw new UnknownPreparedSendMessageException(this.getClientConfiguration().getClientUser(), message);
			}
		}
		this.getClientConfiguration().getIoSession().write(mPushMessageModel);
		return mPushMessageModel;
	}

	@Override
	public ClientConfiguration getClientConfiguration() {
		return this.clientConfiguration;
	}

	public static void main(String[] args) throws MPushMessageException, IOException {
		ClientConfiguration clientConfiguration = new ClientConfiguration("localhost", 12345);
		clientConfiguration.getClientUser().setAuthenticator(new Authenticator() {
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
		mPushMessageClientImpl.sendMessage("hello".getBytes());
	}
}
