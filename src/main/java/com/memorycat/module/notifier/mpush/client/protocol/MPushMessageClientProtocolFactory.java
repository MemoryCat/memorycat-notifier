package com.memorycat.module.notifier.mpush.client.protocol;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import com.memorycat.module.notifier.mpush.client.config.ClientConfiguration;

public class MPushMessageClientProtocolFactory implements ProtocolCodecFactory {

	private MPushMessageClientProtocolDecoder messageProtocolDecoder;
	private MPushMessageClientProtocolEncoder mPushMessageProtocolEncoder;
	private final ClientConfiguration clientConfiguration;

	public MPushMessageClientProtocolFactory(ClientConfiguration clientConfiguration) {
		super();
		this.clientConfiguration = clientConfiguration;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		if (this.mPushMessageProtocolEncoder == null) {
			this.mPushMessageProtocolEncoder = new MPushMessageClientProtocolEncoder(this.clientConfiguration);
		}
		return this.mPushMessageProtocolEncoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		if (this.messageProtocolDecoder == null) {
			this.messageProtocolDecoder = new MPushMessageClientProtocolDecoder(this.clientConfiguration);
		}
		return this.messageProtocolDecoder;
	}

}
