package com.memorycat.module.notifier.mpush.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.module.notifier.mpush.auth.LoginUserAuthenticator;
import com.memorycat.module.notifier.mpush.exception.MPushMessageException;
import com.memorycat.module.notifier.mpush.exception.UnknownPreparedSendMessageException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.model.MPushMessageType;
import com.memorycat.module.notifier.mpush.protocol.MPushMessageProtocolFactory;
import com.memorycat.module.notifier.mpush.server.auth.AuthenticatorServerManger;
import com.memorycat.module.notifier.mpush.server.auth.UserAndPasswordAuthenticator;
import com.memorycat.module.notifier.mpush.server.config.LoginUserManager;
import com.memorycat.module.notifier.mpush.server.config.ServerConfiguration;
import com.memorycat.module.notifier.mpush.server.model.ConnectionAddress;
import com.memorycat.module.notifier.mpush.server.model.LoginUser;
import com.memorycat.module.notifier.mpush.util.Constants;
import com.memorycat.module.notifier.mpush.util.MPushMessageMd5Coder;
import com.memorycat.module.notifier.util.EncryptUtil;

public class MPushMessageServerImpl implements Runnable, MPushMessageServer {
	private static final Logger logger = LoggerFactory.getLogger(MPushMessageServerImpl.class);
	// private IoAcceptor ioAcceptor = new
	// NioSocketAcceptor();//NioDatagramAcceptor();
	private final IoAcceptor ioAcceptor;
	private final ServerConfiguration serverConfiguration;

	public MPushMessageServerImpl(ServerConfiguration serverConfiguration) {
		super();
		if (serverConfiguration == null) {
			throw new NullPointerException(Constants.EXCEPTION_SERVER_INIT_NULLPOINT_CONFIG);
		}
		this.serverConfiguration = serverConfiguration;
		this.serverConfiguration.setmPushMessageServer(this);
		if(serverConfiguration.isUseUdp()) {
			this.ioAcceptor = new NioDatagramAcceptor();
		}else {
			this.ioAcceptor = new NioSocketAcceptor();
		}
	}

	public void run() {
		try {
			// ioAcceptor.getSessionConfig().setReuseAddress(true);
			ioAcceptor.getFilterChain().addLast("logger", new LoggingFilter());

			ioAcceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MPushMessageProtocolFactory()));

			ioAcceptor.setHandler(new ServerMessageHandler(this));
			ioAcceptor.setDefaultLocalAddress(new InetSocketAddress(this.serverConfiguration.getPort()));
			ioAcceptor.bind();

		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public MPushMessageModel sendMessage(LoginUser loginUser, Object message)
			throws MPushMessageException, IOException, Exception {
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
				throw new UnknownPreparedSendMessageException(loginUser, message);
			}
		}

		if ((loginUser.getClientKey() == null)
				&& !(MPushMessageType.AUTH_ENCRYPT_RESPONSE == mPushMessageModel.getMessageType()
						|| MPushMessageType.AUTH_ENCRYPT_RESPONSE_RETRY == mPushMessageModel.getMessageType())) {
			// ?丢弃消息？ 或是发送加密重试请求
			mPushMessageModel = ServerMPushMessageHelper.retryEntrycptment(serverConfiguration, loginUser);
		} else {
			// 处理Model
			if (MPushMessageType.AUTH_ENCRYPT_RESPONSE != mPushMessageModel.getMessageType()
					&& MPushMessageType.AUTH_ENCRYPT_RESPONSE_RETRY != mPushMessageModel.getMessageType()) {
				synchronized (mPushMessageModel) {
					// 加密body
					byte[] encode = EncryptUtil.encode(loginUser.getClientKey(), loginUser.getPrivateKey(),
							mPushMessageModel.getBody());
					mPushMessageModel.setBody(encode);
				}
			}
		}

		// 发送前model最后的处理
		if (mPushMessageModel.getResponseSequence() == 0) {
			this.serverConfiguration.getResponseSequence().incrementAndGet();
		}
		mPushMessageModel
				.setBodyLenth((short) (mPushMessageModel.getBody() != null ? mPushMessageModel.getBody().length : 0));
		// md5验证
		MPushMessageMd5Coder.encodeMPushMessage(mPushMessageModel);

		this.getIoSession(loginUser).write(mPushMessageModel);
		return mPushMessageModel;
	}

	private IoSession getIoSession(LoginUser loginUser) {
		// 先查mina自己管理session有木有，没有的话再自己创建。
		// 因为mina闲时超过1分钟钟就自动断开，所以memorycat-notifier使用自己的链接管理机制
		IoSession ioSession = null;
		Map<Long, IoSession> managedSessions = this.ioAcceptor.getManagedSessions();
		for (Entry<Long, IoSession> e : managedSessions.entrySet()) {
			IoSession value = e.getValue();
			SocketAddress remoteAddress = value.getRemoteAddress();
			if (remoteAddress instanceof InetSocketAddress) {
				InetSocketAddress inetSocketAddress = (InetSocketAddress) remoteAddress;
				if (new ConnectionAddress(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress.getPort())
						.equals(loginUser.getConnectionAddress())) {
					ioSession = value;
					// logger.trace("使用Mina管理下的IoSession");
					break;
				}
			}
		}
		if (ioSession == null) {
			ioSession = this.ioAcceptor.newSession(new InetSocketAddress(loginUser.getConnectionAddress().getIp(),
					loginUser.getConnectionAddress().getPort()), this.ioAcceptor.getLocalAddress());
			logger.trace("Mina管理下的链接已经断开，尝试重新使用新的链接进行原地址通讯。" + ioSession);
		}
		return ioSession;
	}

	@Override
	public ServerConfiguration getServerConfiguration() {
		return this.serverConfiguration;
	}

	public static void main(String[] args) throws InterruptedException {
		ServerConfiguration serverConfiguration = new ServerConfiguration(12345,false);
		serverConfiguration.setHeartBeatSeconds(5);
		serverConfiguration.setHeartBeatFailedCount(3);
		// serverConfiguration.setSendDbMessagePeriod(5000L);
		serverConfiguration.setLoginUserManager(new LoginUserManager());
		serverConfiguration.setLoginUserAuthenticator(new LoginUserAuthenticator());
		AuthenticatorServerManger authenticatorServerManger = new AuthenticatorServerManger();
		serverConfiguration.setAuthenticatorServerManger(authenticatorServerManger);
		authenticatorServerManger.register(new UserAndPasswordAuthenticator());

		MPushMessageServerImpl server = new MPushMessageServerImpl(serverConfiguration);
		Thread thread = new Thread(server);
		thread.start();
		logger.info("server is running");
	}

}
