package com.memorycat.module.notifier.mpush.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.module.notifier.mpush.exception.MPushMessageException;
import com.memorycat.module.notifier.mpush.exception.UnknownPreparedSendMessageException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.protocol.MPushMessageProtocolFactory;

public class MPushMessageServerImpl implements Runnable, MPushMessageServer {
	private static final Logger logger = LoggerFactory.getLogger(MPushMessageServerImpl.class);
	private NioDatagramAcceptor nioDatagramAcceptor = new NioDatagramAcceptor();

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

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new MPushMessageServerImpl());
		thread.start();
		logger.info("server is running");
		thread.join();
		logger.info("server stoped");
	}

	@Override
	public MPushMessageModel sendMessage(Object context, Object message) throws MPushMessageException, IOException {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		if (message instanceof MPushMessageModel) {
			mPushMessageModel = (MPushMessageModel) message;
		} else if (message instanceof String) {
			mPushMessageModel.setBody(((String) message).getBytes());
		} else if (message instanceof byte[]) {
			mPushMessageModel.setBody((byte[]) message);
		} else {
			throw new UnknownPreparedSendMessageException(context, message);
		}
		
		//TODO 怎么发消息？
		return mPushMessageModel;
	}
}
