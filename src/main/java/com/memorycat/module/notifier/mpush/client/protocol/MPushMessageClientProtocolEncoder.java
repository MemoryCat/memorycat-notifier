package com.memorycat.module.notifier.mpush.client.protocol;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.module.notifier.mpush.client.ClientMPushMessageHelper;
import com.memorycat.module.notifier.mpush.client.config.ClientConfiguration;
import com.memorycat.module.notifier.mpush.client.model.ClientUser;
import com.memorycat.module.notifier.mpush.exception.MPushMessageException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.model.MPushMessageType;
import com.memorycat.module.notifier.mpush.util.Constants;
import com.memorycat.module.notifier.mpush.util.MPushMessageMd5Coder;
import com.memorycat.module.notifier.mpush.util.MPushMessageUtil;
import com.memorycat.module.notifier.util.DhEncryptUtil;

class MPushMessageClientProtocolEncoder implements ProtocolEncoder {

	private static final Logger logger = LoggerFactory.getLogger(MPushMessageClientProtocolEncoder.class);
	private final ClientConfiguration clientConfiguration;

	public MPushMessageClientProtocolEncoder(ClientConfiguration clientConfiguration) {
		super();
		this.clientConfiguration = clientConfiguration;
	}

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		MPushMessageModel mPushMessageModel = null;
		if (message instanceof MPushMessageModel) {
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

		if (mPushMessageModel.getRequestSequence() == 0) {
			mPushMessageModel.setRequestSequence(this.clientConfiguration.getRequestSequence().incrementAndGet());
		}

		ClientUser clientUser = this.clientConfiguration.getClientUser();
		if ((clientUser.getServerKey() == null || clientUser.getServerKey().length == 0)
				&& !(MPushMessageType.AUTH_ENCRYPT_REQUEST == mPushMessageModel.getMessageType())) {

			//TODO 此处有bug，序列不再是+1了，这里创造的MPushMessageModel又再一次+1了
			
			// ?丢弃消息？ 或是发送加密重试请求
			logger.info("丢弃消息，因为发送消息前未和服务器进行密钥交换。"+mPushMessageModel);
			MPushMessageModel encryptMsg = ClientMPushMessageHelper.requestEncrypt(clientConfiguration);
			// 设置协议头的body长度
			encryptMsg.setBodyLenth((short) encryptMsg.getBody().length);
			// md5验证
			MPushMessageMd5Coder.encodeMPushMessage(encryptMsg);
			out.write(IoBuffer.wrap(MPushMessageUtil.toByteArray(encryptMsg)));
			return;
		} else {
			// 处理Model
			IoBuffer wrap = null;
			synchronized (mPushMessageModel) {
				// 加密body
				byte[] encode = DhEncryptUtil.encode(clientUser.getServerKey(), clientUser.getPrivateKey(),
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

	@Override
	public void dispose(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

}
