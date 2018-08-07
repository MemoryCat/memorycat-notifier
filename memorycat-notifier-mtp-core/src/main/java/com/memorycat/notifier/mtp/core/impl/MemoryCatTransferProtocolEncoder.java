package com.memorycat.notifier.mtp.core.impl;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.notifier.mtp.core.entity.MtpEntity;
import com.memorycat.notifier.mtp.core.exception.UnformatMessageException;
import com.memorycat.notifier.mtp.core.util.MtpEntityMd5Coder;
import com.memorycat.notifier.mtp.core.util.MtpEntitySerializer;

public class MemoryCatTransferProtocolEncoder implements ProtocolEncoder {

	private static final Logger logger = LoggerFactory.getLogger(MemoryCatTransferProtocolEncoder.class);

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		if (!(message instanceof MtpEntity)) {
			logger.debug("丢弃消息，在IoSession#write()前应该转换为MtpEntity格式");
			throw new UnformatMessageException(message);
		}
		MtpEntity mtpEntity = (MtpEntity) message;
		mtpEntity.setBodyLenth(mtpEntity.getBody().length);

		out.write(IoBuffer.wrap(MtpEntitySerializer.serialize(MtpEntityMd5Coder.encode(mtpEntity))));
	}

	@Override
	public void dispose(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

}
