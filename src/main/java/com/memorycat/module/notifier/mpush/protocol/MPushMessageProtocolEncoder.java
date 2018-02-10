package com.memorycat.module.notifier.mpush.protocol;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.memorycat.module.notifier.mpush.exception.MPushMessageException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.util.Constants;
import com.memorycat.module.notifier.mpush.util.MPushMessageMd5Coder;
import com.memorycat.module.notifier.mpush.util.MPushMessageUtil;

public class MPushMessageProtocolEncoder implements ProtocolEncoder {

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		if (message instanceof MPushMessageModel) {
			mPushMessageModel = (MPushMessageModel) message;
		} else if (message instanceof String) {
			mPushMessageModel.setBody(message.toString().getBytes());
		} else if (message instanceof Byte[]) {
			mPushMessageModel.setBody((byte[]) message);
		} else {
			throw new MPushMessageException(mPushMessageModel, Constants.EXCEPTION_UNSUPPORTEDTYPE);
		}
		
		
		// TODO处理Model
		mPushMessageModel.setBodyLenth((short) mPushMessageModel.getBody().length);
		MPushMessageMd5Coder.encodeMPushMessage(mPushMessageModel);
		out.write(IoBuffer.wrap(MPushMessageUtil.toByteArray(mPushMessageModel)));
	}

	@Override
	public void dispose(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

}
