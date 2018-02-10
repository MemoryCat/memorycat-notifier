package com.memorycat.module.notifier.mpush.client;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;

import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.model.MPushMessageType;
import com.memorycat.module.notifier.mpush.protocol.MPushMessageProtocolFactory;

public class MPushMessageClientImpl {
	public static void main(String[] args) throws Exception {
		NioDatagramConnector nioDatagramConnector = new NioDatagramConnector();
		nioDatagramConnector.getSessionConfig().setReuseAddress(true);
		nioDatagramConnector.getFilterChain().addFirst("log", new LoggingFilter());
		nioDatagramConnector.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new MPushMessageProtocolFactory()));
		nioDatagramConnector.setHandler(new IoHandlerAdapter() {

			@Override
			public void messageReceived(IoSession session, Object message) throws Exception {
				System.out.println("client:" + message);
			}

		});
		ConnectFuture connectFuture = nioDatagramConnector.connect(new InetSocketAddress("localhost", 12345));
		connectFuture.await();
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.AUTH_LOGIN_REQUEST);
		mPushMessageModel.setBody("asdasd".getBytes());
		mPushMessageModel.setRequestSequence(4L);
		mPushMessageModel.setResponseSequence(555555L);
		mPushMessageModel.setBodyLenth((short) mPushMessageModel.getBody().length);
		connectFuture.getSession().write(mPushMessageModel);

	}
}
