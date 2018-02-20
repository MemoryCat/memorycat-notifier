package com.memorycat.module.notifier.mpush.server.protocol;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.module.notifier.mpush.exception.MPushMessageException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.model.MPushMessageType;
import com.memorycat.module.notifier.mpush.server.config.ServerConfiguration;
import com.memorycat.module.notifier.mpush.util.Constants;
import com.memorycat.module.notifier.mpush.util.MPushMessageMd5Coder;
import com.memorycat.module.notifier.mpush.util.MPushMessageUtil;

class MPushMessageServerProtocolEncoder implements ProtocolEncoder {

	private static final Logger logger = LoggerFactory.getLogger(MPushMessageServerProtocolEncoder.class);

	private final ServerConfiguration serverConfiguration;

	public MPushMessageServerProtocolEncoder(ServerConfiguration serverConfiguration) {
		super();
		this.serverConfiguration = serverConfiguration;
	}

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		MPushMessageModel mPushMessageModel = null;
		if (message instanceof MPushMessageModel) {
			mPushMessageModel = new MPushMessageModel();
			mPushMessageModel = (MPushMessageModel) message;
		} else {
			logger.debug("非合法MPushMessageModel类型消息，无法自动构造协议头部分字段。请尽量传入MPushMessageModel类型的消息");
			mPushMessageModel = new MPushMessageModel();
			mPushMessageModel.setMessageType(MPushMessageType.COMMON_MESSAGE);
			if (message instanceof String) {
				mPushMessageModel.setBody(message.toString().getBytes());
			} else if (message instanceof Byte[]) {
				mPushMessageModel.setBody((byte[]) message);
			} else {
				throw new MPushMessageException(mPushMessageModel, Constants.EXCEPTION_UNSUPPORTEDTYPE);
			}
		}
		// 处理Model
		IoBuffer wrap = null;
		synchronized (mPushMessageModel) {
			mPushMessageModel.setBodyLenth((short) mPushMessageModel.getBody().length);
			MPushMessageMd5Coder.encodeMPushMessage(mPushMessageModel);
			wrap = IoBuffer.wrap(MPushMessageUtil.toByteArray(mPushMessageModel));
		}
		out.write(wrap);
	}

	@Override
	public void dispose(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

}
