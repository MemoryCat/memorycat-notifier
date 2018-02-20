package com.memorycat.module.notifier.mpush.server.protocol;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import com.memorycat.module.notifier.mpush.server.config.ServerConfiguration;

public class MPushMessageServerProtocolFactory implements ProtocolCodecFactory {

	private MPushMessageServerProtocolDecoder messageProtocolDecoder;
	private MPushMessageServerProtocolEncoder mPushMessageProtocolEncoder;
	private final ServerConfiguration serverConfiguration;

	public MPushMessageServerProtocolFactory(ServerConfiguration serverConfiguration) {
		super();
		this.serverConfiguration = serverConfiguration;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		if (this.mPushMessageProtocolEncoder == null) {
			this.mPushMessageProtocolEncoder = new MPushMessageServerProtocolEncoder(serverConfiguration);
		}
		return this.mPushMessageProtocolEncoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		if (this.messageProtocolDecoder == null) {
			this.messageProtocolDecoder = new MPushMessageServerProtocolDecoder(serverConfiguration);
		}
		return this.messageProtocolDecoder;
	}

}
