package com.memorycat.module.notifier.mpush.util;

import com.memorycat.module.notifier.mpush.exception.MPushMessageDeserializeException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.model.MPushMessageType;
import com.memorycat.module.notifier.util.ByteUtil;
import com.memorycat.module.notifier.util.MPushMessageModelConstants;

public class MPushMessageUtil {

	/**
	 * 序列化
	 * 
	 * @param mPushMessageModel
	 * @return
	 */
	public static byte[] toByteArray(MPushMessageModel mPushMessageModel) {

		if (mPushMessageModel == null) {
			throw new NullPointerException();
		}

		byte[] returnValue = new byte[MPushMessageModelConstants.OFFSET_MESSAGE_BODY
				+ mPushMessageModel.getBody().length];
		System.arraycopy(mPushMessageModel.getPrefix().getBytes(), 0, returnValue, 0,
				mPushMessageModel.getPrefix().length());
		returnValue[MPushMessageModelConstants.OFFSET_MESSAGE_VERSION] = mPushMessageModel.getVersion();
		// type
		System.arraycopy(ByteUtil.shortToByteArray(mPushMessageModel.getMessageType().getTypeCode()), 0, returnValue,
				MPushMessageModelConstants.OFFSET_MESSAGE_TYPE, 2);
		// timestamp
		System.arraycopy(ByteUtil.longToByteArray(mPushMessageModel.getTimeStmap()), 0, returnValue,
				MPushMessageModelConstants.OFFSET_MESSAGE_TIMESTAMP, 8);
		// requestSequence
		System.arraycopy(ByteUtil.longToByteArray(mPushMessageModel.getRequestSequence()), 0, returnValue,
				MPushMessageModelConstants.OFFSET_MESSAGE_SEQUENCE_REQUEST,
				MPushMessageModelConstants.SIZE_MESSAGE_SEQUENCE_REQUEST);
		// responseSequence
		System.arraycopy(ByteUtil.longToByteArray(mPushMessageModel.getResponseSequence()), 0, returnValue,
				MPushMessageModelConstants.OFFSET_MESSAGE_SEQUENCE_RESPONSE,
				MPushMessageModelConstants.SIZE_MESSAGE_SEQUENCE_RESPONSE);

		// body length
		System.arraycopy(ByteUtil.shortToByteArray(mPushMessageModel.getBodyLenth()), 0, returnValue,
				MPushMessageModelConstants.OFFSET_MESSAGE_BODY_LENGTH, 2);
		// md5
		System.arraycopy(mPushMessageModel.getMd5Verification(), 0, returnValue,
				MPushMessageModelConstants.OFFSET_MESSAGE_MD5, 16);
		// body
		if (mPushMessageModel.getBody().length > 0) {
			System.arraycopy(mPushMessageModel.getBody(), 0, returnValue,
					MPushMessageModelConstants.OFFSET_MESSAGE_BODY, mPushMessageModel.getBody().length);
		}
		return returnValue;
	}

	/**
	 * 反序列化
	 * 
	 * @param data
	 * @return
	 * @throws MPushMessageDeserializeException
	 *             如果data长度小于MPushMessageModel最小大小；如果反序列化时协议头的bodyLength小于0(可能由溢出导致负数)
	 */
	public static MPushMessageModel fromByteArray(byte[] data) throws MPushMessageDeserializeException {
		if (data == null) {
			throw new NullPointerException();
		}
		if (data.length < MPushMessageModelConstants.OFFSET_MESSAGE_BODY) {
			throw new MPushMessageDeserializeException(null,
					Constants.EXCEPTION_NOTIFIER_MPUSH_MPUSHMESSAGEMODEL_DATAMISS);
		}
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setVersion(data[MPushMessageModelConstants.OFFSET_MESSAGE_VERSION]);
		mPushMessageModel.setMessageType(MPushMessageType
				.value(ByteUtil.byteArrayToShort(data, MPushMessageModelConstants.OFFSET_MESSAGE_TYPE)));
		mPushMessageModel
				.setTimeStmap(ByteUtil.byteArrayToLong(data, MPushMessageModelConstants.OFFSET_MESSAGE_TIMESTAMP));
		mPushMessageModel.setRequestSequence(
				ByteUtil.byteArrayToLong(data, MPushMessageModelConstants.OFFSET_MESSAGE_SEQUENCE_REQUEST));
		mPushMessageModel.setResponseSequence(
				ByteUtil.byteArrayToLong(data, MPushMessageModelConstants.OFFSET_MESSAGE_SEQUENCE_RESPONSE));

		// body length
		mPushMessageModel
				.setBodyLenth(ByteUtil.byteArrayToShort(data, MPushMessageModelConstants.OFFSET_MESSAGE_BODY_LENGTH));
		// md5
		byte[] md5 = new byte[16];
		System.arraycopy(data, MPushMessageModelConstants.OFFSET_MESSAGE_MD5, md5, 0, 16);
		mPushMessageModel.setMd5Verification(md5);
		if (mPushMessageModel.getBodyLenth() < 0) {
			throw new MPushMessageDeserializeException(mPushMessageModel,
					"mPushMessageModel的body长度(" + mPushMessageModel.getBodyLenth() + ")不符合规范，可能是udp接收消息错误");
		}
		byte[] body = new byte[mPushMessageModel.getBodyLenth()];
		System.arraycopy(data, MPushMessageModelConstants.OFFSET_MESSAGE_BODY, body, 0,
				mPushMessageModel.getBodyLenth());
		mPushMessageModel.setBody(body);
		return mPushMessageModel;
	}

}
