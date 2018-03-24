package com.memorycat.module.notifier.mpush.util;

import java.security.NoSuchAlgorithmException;

import com.memorycat.module.notifier.mpush.exception.MPushMessageMd5EncodeException;
import com.memorycat.module.notifier.mpush.exception.MPushMessageMd5VerifyException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.model.MPushMessageType;
import com.memorycat.module.notifier.util.MPushMessageModelConstants;
import com.memorycat.module.notifier.util.Md5Util;

public class MPushMessageMd5Coder {

	/**
	 * 填充MPushMessageModel协议头的md5校验部分
	 * 
	 * @param mPushMessageModel
	 * @return 原来传入的参数
	 * @throws NoSuchAlgorithmException
	 *             找不到md5实现
	 * @throws MPushMessageMd5EncodeException
	 *             md5算法的长度不是和模型一致
	 */
	public static MPushMessageModel encodeMPushMessage(MPushMessageModel mPushMessageModel)
			throws NoSuchAlgorithmException, MPushMessageMd5EncodeException {

		byte[] data = MPushMessageUtil.toByteArray(mPushMessageModel);
		int end = MPushMessageModelConstants.OFFSET_MESSAGE_MD5 + MPushMessageModelConstants.SIZE_MESSAGE_MD5;
		for (int i = MPushMessageModelConstants.OFFSET_MESSAGE_MD5; i < end; i++) {
			data[i] = 0;
		}
		byte[] encode = Md5Util.encode(data);
		if (encode.length != MPushMessageModelConstants.SIZE_MESSAGE_MD5) {
			throw new MPushMessageMd5EncodeException(mPushMessageModel,
					Constants.EXCEPTION_NOTIFIER_MPUSH_VERIFT_MD5_LENGTH_INCORRECT);
		}
		mPushMessageModel.setMd5Verification(encode);
		return mPushMessageModel;
	}

	/**
	 * 验证MpushMessageModel的md5值是否正确
	 * 
	 * @param mPushMessageModel
	 * @return true:正确;false:不正确
	 * @throws NoSuchAlgorithmException
	 *             找不到md5实现
	 * @throws MPushMessageMd5VerifyException
	 *             md5算法的长度不是和模型一致
	 */
	public static boolean verifyMPushMessageMd5Value(MPushMessageModel mPushMessageModel)
			throws NoSuchAlgorithmException, MPushMessageMd5VerifyException {
		byte[] data = MPushMessageUtil.toByteArray(mPushMessageModel);
		int end = MPushMessageModelConstants.OFFSET_MESSAGE_MD5 + MPushMessageModelConstants.SIZE_MESSAGE_MD5;
		for (int i = MPushMessageModelConstants.OFFSET_MESSAGE_MD5; i < end; i++) {
			data[i] = 0;
		}
		byte[] encode = Md5Util.encode(data);
		if (encode.length != MPushMessageModelConstants.SIZE_MESSAGE_MD5) {
			throw new MPushMessageMd5VerifyException(mPushMessageModel,
					Constants.EXCEPTION_NOTIFIER_MPUSH_VERIFT_MD5_LENGTH_INCORRECT);
		}
		byte[] data2 = mPushMessageModel.getMd5Verification();
		for (int i = 0; i < MPushMessageModelConstants.SIZE_MESSAGE_MD5; i++) {
			if (encode[i] != data2[i]) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws MPushMessageMd5EncodeException, NoSuchAlgorithmException, CloneNotSupportedException, MPushMessageMd5VerifyException {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.UNKOWN);
		mPushMessageModel.setBody("asdasd".getBytes());
		mPushMessageModel.setBodyLenth((short) mPushMessageModel.getBody().length);
		MPushMessageModel clone = mPushMessageModel.clone();
		
		MPushMessageModel encodeMPushMessage = MPushMessageMd5Coder.encodeMPushMessage(mPushMessageModel);
		System.out.println(MPushMessageMd5Coder.verifyMPushMessageMd5Value(clone));
		System.out.println(MPushMessageMd5Coder.verifyMPushMessageMd5Value(encodeMPushMessage));
	}
}
