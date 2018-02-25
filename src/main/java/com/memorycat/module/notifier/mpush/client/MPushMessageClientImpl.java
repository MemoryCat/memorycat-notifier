package com.memorycat.module.notifier.mpush.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
import com.memorycat.module.notifier.mpush.client.model.ClientUser;
import com.memorycat.module.notifier.mpush.exception.MPushMessageException;
import com.memorycat.module.notifier.mpush.exception.UnknownPreparedSendMessageException;
import com.memorycat.module.notifier.mpush.exception.auth.AuthenticationException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.model.MPushMessageType;
import com.memorycat.module.notifier.mpush.protocol.MPushMessageProtocolFactory;
import com.memorycat.module.notifier.mpush.util.MPushMessageMd5Coder;
import com.memorycat.module.notifier.util.DhEncryptUtil;

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
	public MPushMessageModel sendMessage(Object message) throws MPushMessageException, IOException, Exception {
		MPushMessageModel mPushMessageModel = null;
		if (message instanceof MPushMessageModel) {
			mPushMessageModel = (MPushMessageModel) message;
		} else {
			mPushMessageModel = new MPushMessageModel();
			if (message instanceof String) {
				mPushMessageModel.setBody(((String) message).getBytes());
			} else if (message instanceof byte[]) {
				mPushMessageModel.setBody((byte[]) message);
			} else if (message instanceof Serializable) {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
				objectOutputStream.writeObject((Serializable) message);
				objectOutputStream.close();
				mPushMessageModel.setBody(byteArrayOutputStream.toByteArray());
			} else {
				throw new UnknownPreparedSendMessageException(this.getClientConfiguration().getClientUser(), message);
			}
		}

		ClientUser clientUser = this.getClientConfiguration().getClientUser();

		if ((clientUser.getServerKey() == null || clientUser.getServerKey().length == 0)
				&& !(MPushMessageType.AUTH_ENCRYPT_REQUEST == mPushMessageModel.getMessageType())) {
			// ?丢弃消息？ 或是发送加密重试请求
			logger.debug("丢弃原消息，应先请求加密。" + mPushMessageModel);
			mPushMessageModel = ClientMPushMessageHelper.requestEncrypt(getClientConfiguration());
		} else {
			// 处理Model
			if (MPushMessageType.AUTH_ENCRYPT_REQUEST != mPushMessageModel.getMessageType()) {
				synchronized (mPushMessageModel) {
					// 加密body
					byte[] encode = DhEncryptUtil.encode(clientUser.getServerKey(), clientUser.getPrivateKey(),
							mPushMessageModel.getBody());
					mPushMessageModel.setBody(encode);
				}
			}
		}

		// 发送前model最后的处理
		if (mPushMessageModel.getRequestSequence() == 0) {
			this.clientConfiguration.getRequestSequence().incrementAndGet();
		}
		mPushMessageModel
				.setBodyLenth((short) (mPushMessageModel.getBody() != null ? mPushMessageModel.getBody().length : 0));
		// md5验证
		MPushMessageMd5Coder.encodeMPushMessage(mPushMessageModel);

		this.getClientConfiguration().getIoSession().write(mPushMessageModel);
		return mPushMessageModel;
	}

	@Override
	public ClientConfiguration getClientConfiguration() {
		return this.clientConfiguration;
	}

	public static void main(String[] args) throws Exception {
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
