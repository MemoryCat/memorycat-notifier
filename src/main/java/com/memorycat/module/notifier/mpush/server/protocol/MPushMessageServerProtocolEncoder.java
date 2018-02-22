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
import com.memorycat.module.notifier.mpush.server.ServerMPushMessageHelper;
import com.memorycat.module.notifier.mpush.server.config.ServerConfiguration;
import com.memorycat.module.notifier.mpush.server.model.LoginUser;
import com.memorycat.module.notifier.mpush.util.Constants;
import com.memorycat.module.notifier.mpush.util.MPushMessageMd5Coder;
import com.memorycat.module.notifier.mpush.util.MPushMessageUtil;
import com.memorycat.module.notifier.util.DhEncryptUtil;

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
			logger.debug("非合法MPushMessageModel类型消息，无法自动构造某些协议头字段。请尽量传入MPushMessageModel类型的消息。" + message);
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

		if (mPushMessageModel.getResponseSequence() == 0) {
			mPushMessageModel.setResponseSequence(this.serverConfiguration.getResponseSequence().incrementAndGet());
		}

		LoginUser loginUser = this.serverConfiguration.getLoginUserManager().getLoginUser(session);
		if ((loginUser.getClientKey() == null || loginUser.getClientKey().length == 0)
				&& !(MPushMessageType.AUTH_ENCRYPT_RESPONSE == mPushMessageModel.getMessageType()
						|| MPushMessageType.AUTH_ENCRYPT_RESPONSE_RETRY == mPushMessageModel.getMessageType())) {
			// ?丢弃消息？ 或是发送加密重试请求
			out.write(IoBuffer.wrap(MPushMessageUtil
					.toByteArray(ServerMPushMessageHelper.retryEntrycptment(serverConfiguration, loginUser))));
			return;
		} else {
			// 处理Model
			if (MPushMessageType.AUTH_ENCRYPT_RESPONSE == mPushMessageModel.getMessageType()
					|| MPushMessageType.AUTH_ENCRYPT_RESPONSE_RETRY == mPushMessageModel.getMessageType()) {
				mPushMessageModel.setBodyLenth((short) mPushMessageModel.getBody().length);
				MPushMessageMd5Coder.encodeMPushMessage(mPushMessageModel);
				out.write(IoBuffer.wrap(MPushMessageUtil.toByteArray(mPushMessageModel)));
				return;
			} else {
				IoBuffer wrap = null;
				synchronized (mPushMessageModel) {
					// 加密body
					byte[] encode = DhEncryptUtil.encode(loginUser.getClientKey(), loginUser.getPrivateKey(),
							mPushMessageModel.getBody());
					mPushMessageModel.setBody(encode);
					// 设置协议头的body长度
					mPushMessageModel.setBodyLenth((short) mPushMessageModel.getBody().length);
					// md5验证
					MPushMessageMd5Coder.encodeMPushMessage(mPushMessageModel);
					wrap = IoBuffer.wrap(MPushMessageUtil.toByteArray(mPushMessageModel));
				}
				out.write(wrap);
				return;
			}
		}
	}

	@Override
	public void dispose(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

}
