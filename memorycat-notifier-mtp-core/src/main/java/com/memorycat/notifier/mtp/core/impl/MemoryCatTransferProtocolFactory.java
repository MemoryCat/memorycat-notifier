package com.memorycat.notifier.mtp.core.impl;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class MemoryCatTransferProtocolFactory implements ProtocolCodecFactory {

	private ProtocolEncoder protocolEncoder;
	private ProtocolDecoder protocolDecoder;

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		if (this.protocolEncoder == null) {
			synchronized (this) {
				if (this.protocolEncoder == null) {
					this.protocolEncoder = new MemoryCatTransferProtocolEncoder();
				}
			}
		}
		return this.protocolEncoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		if (this.protocolDecoder == null) {
			synchronized (this) {
				if (this.protocolDecoder == null) {
					this.protocolDecoder = new MemoryCatTransferProtocolDecoder();
				}
			}
		}
		return this.protocolDecoder;
	}

}
