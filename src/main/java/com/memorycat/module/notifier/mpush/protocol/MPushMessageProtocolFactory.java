package com.memorycat.module.notifier.mpush.protocol;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class MPushMessageProtocolFactory implements ProtocolCodecFactory {

	private MPushMessageProtocolDecoder messageProtocolDecoder;
	private MPushMessageProtocolEncoder mPushMessageProtocolEncoder;

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		if (this.mPushMessageProtocolEncoder == null) {
			this.mPushMessageProtocolEncoder = new MPushMessageProtocolEncoder();
		}
		return this.mPushMessageProtocolEncoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		if (this.messageProtocolDecoder == null) {
			this.messageProtocolDecoder = new MPushMessageProtocolDecoder();
		}
		return this.messageProtocolDecoder;
	}

}
