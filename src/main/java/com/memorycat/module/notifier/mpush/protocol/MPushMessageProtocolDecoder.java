package com.memorycat.module.notifier.mpush.protocol;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.module.notifier.mpush.exception.MPushMessageDecodeException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.util.MPushMessageMd5Coder;
import com.memorycat.module.notifier.mpush.util.MPushMessageUtil;
import com.memorycat.module.notifier.util.MPushMessageModelConstants;

public class MPushMessageProtocolDecoder implements ProtocolDecoder {

	private static final Logger logger = LoggerFactory.getLogger(MPushMessageProtocolDecoder.class);

	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		int limit = in.limit();
		byte[] buf = new byte[limit];
		in.get(buf);
		if (limit > MPushMessageModelConstants.OFFSET_MESSAGE_BODY) {
			MPushMessageModel mPushMessageModel = MPushMessageUtil.fromByteArray(buf);
			if (MPushMessageMd5Coder.verifyMPushMessageMd5Value(mPushMessageModel)) {
				out.write(mPushMessageModel);
			} else {
				String msg = "数据包md5校验不正确，丢弃数据：" + new String(buf);
				logger.info(msg);
				throw new MPushMessageDecodeException(mPushMessageModel, msg);
			}
		} else {
			String msg = "数据包不完整，丢弃数据：" + new String(buf);
			logger.info(msg);
		}
	}

	@Override
	public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

}
