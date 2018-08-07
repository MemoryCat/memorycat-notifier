package com.memorycat.notifier.mtp.client.impl;

import org.apache.mina.core.service.IoConnector;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.memorycat.notifier.mtp.client.ConnectionConfig;
import com.memorycat.notifier.mtp.core.impl.MemoryCatTransferProtocolFactory;

public class DefaultConnectionConfig implements ConnectionConfig {

	private final int port;
	private final String hostAddress;
	private final IoConnector ioConnector;

	public DefaultConnectionConfig() {
		this("127.0.0.1", 12345);

	}

	public DefaultConnectionConfig(String hostAddress, int port) {
		super();
		if (hostAddress == null || port <= 0) {
			throw new NullPointerException();
		}
		this.hostAddress = hostAddress;
		this.port = port;
		this.ioConnector = new NioSocketConnector();
//		this.ioConnector.getFilterChain().addLast("logger", new LoggingFilter());
		this.ioConnector.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new MemoryCatTransferProtocolFactory()));
	}

	@Override
	public boolean usingTcp() {
		return true;
	}

	@Override
	public int getPort() {
		return this.port;
	}

	@Override
	public String getHostAddress() {
		return this.hostAddress;
	}

	@Override
	public boolean blocking() {
		return true;
	}

	@Override
	public IoConnector getIoConnector() {
		return this.ioConnector;
	}

}
