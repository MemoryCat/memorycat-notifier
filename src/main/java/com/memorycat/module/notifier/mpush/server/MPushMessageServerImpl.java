package com.memorycat.module.notifier.mpush.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.module.notifier.mpush.auth.LoginUserAuthenticator;
import com.memorycat.module.notifier.mpush.exception.MPushMessageException;
import com.memorycat.module.notifier.mpush.exception.UnknownPreparedSendMessageException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.protocol.MPushMessageProtocolFactory;
import com.memorycat.module.notifier.mpush.server.auth.AuthenticatorServerManger;
import com.memorycat.module.notifier.mpush.server.auth.UserAndPasswordAuthenticator;
import com.memorycat.module.notifier.mpush.server.config.LoginUserManager;
import com.memorycat.module.notifier.mpush.server.config.ServerConfiguration;
import com.memorycat.module.notifier.mpush.server.model.LoginUser;

public class MPushMessageServerImpl implements Runnable, MPushMessageServer {
	private static final Logger logger = LoggerFactory.getLogger(MPushMessageServerImpl.class);
	private NioDatagramAcceptor nioDatagramAcceptor = new NioDatagramAcceptor();
	private final ServerConfiguration serverConfiguration;

	public MPushMessageServerImpl(ServerConfiguration serverConfiguration) {
		super();
		this.serverConfiguration = serverConfiguration;
	}

	public void run() {
		try {
			nioDatagramAcceptor.getSessionConfig().setReuseAddress(true);
			nioDatagramAcceptor.getFilterChain().addLast("logger", new LoggingFilter());

			nioDatagramAcceptor.getFilterChain().addLast("codec",
					new ProtocolCodecFilter(new MPushMessageProtocolFactory()));

			nioDatagramAcceptor.setHandler(new ServerMessageHandler(this));
			nioDatagramAcceptor.setDefaultLocalAddress(new InetSocketAddress(12345));
			nioDatagramAcceptor.bind();

		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public MPushMessageModel sendMessage(LoginUser loginUser, Object message)
			throws MPushMessageException, IOException {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		if (message instanceof MPushMessageModel) {
			mPushMessageModel = (MPushMessageModel) message;
		} else if (message instanceof String) {
			mPushMessageModel.setBody(((String) message).getBytes());
		} else if (message instanceof byte[]) {
			mPushMessageModel.setBody((byte[]) message);
		} else {
			throw new UnknownPreparedSendMessageException(loginUser, message);
		}

		// TODO 怎么发消息？
		// TODO 先查mina自己管理session有木有，没有的话再自己创建。
		// 因为mina闲时超过1分钟钟就自动断开，所以memorycat-notifier使用自己的链接管理机制
		// Map<Long, IoSession> managedSessions =
		// this.nioDatagramAcceptor.getManagedSessions();
		// for (Entry<Long, IoSession> e : managedSessions.entrySet()) {
		// IoSession ioSession = e.getValue();
		// ioSession.write(mPushMessageModel);
		// }
		IoSession ioSession = this.nioDatagramAcceptor
				.newSession(
						new InetSocketAddress(loginUser.getConnectionAddress().getIp(),
								loginUser.getConnectionAddress().getPort()),
						this.nioDatagramAcceptor.getLocalAddress());

		ioSession.write(mPushMessageModel);

		return mPushMessageModel;
	}

	@Override
	public ServerConfiguration getServerConfiguration() {
		return this.serverConfiguration;
	}

	public static void main(String[] args) throws InterruptedException {
		ServerConfiguration serverConfiguration = new ServerConfiguration();
		serverConfiguration.setLoginUserManager(new LoginUserManager());
		serverConfiguration.setLoginUserAuthenticator(new LoginUserAuthenticator());
		AuthenticatorServerManger authenticatorServerManger = new AuthenticatorServerManger();
		serverConfiguration.setAuthenticatorServerManger(authenticatorServerManger);
		authenticatorServerManger.register(new UserAndPasswordAuthenticator());
		MPushMessageServerImpl server = new MPushMessageServerImpl(serverConfiguration);
		serverConfiguration.setmPushMessageServer(server);
		Thread thread = new Thread(server);
		thread.start();
		logger.info("server is running");
	}

}
