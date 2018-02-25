package com.memorycat.module.notifier.mpush.protocol;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.module.notifier.mpush.exception.UnknownPreparedSendMessageException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.util.MPushMessageMd5Coder;
import com.memorycat.module.notifier.mpush.util.MPushMessageUtil;

public class MPushMessageProtocolEncoder implements ProtocolEncoder {

	private static final Logger logger = LoggerFactory.getLogger(MPushMessageProtocolEncoder.class);

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		if (!(message instanceof MPushMessageModel)) {
			logger.debug("丢弃消息，在IoSession#write()前应该转换为MPushMessageModel格式");
			throw new UnknownPreparedSendMessageException(session, message);
		}
		MPushMessageModel mPushMessageModel = (MPushMessageModel) message;
		// 设置协议头的body长度
		mPushMessageModel.setBodyLenth((short) mPushMessageModel.getBody().length);
		MPushMessageMd5Coder.encodeMPushMessage(mPushMessageModel);
		out.write(IoBuffer.wrap(MPushMessageUtil.toByteArray(mPushMessageModel)));
	}

	@Override
	public void dispose(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

}
