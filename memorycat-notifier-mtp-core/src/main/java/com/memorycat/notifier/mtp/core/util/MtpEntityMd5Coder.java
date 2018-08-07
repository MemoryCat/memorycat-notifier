package com.memorycat.notifier.mtp.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.notifier.mtp.core.entity.MessageType;
import com.memorycat.notifier.mtp.core.entity.MtpEntity;
import com.memorycat.notifier.mtp.core.entity.SendFrom;
import com.memorycat.notifier.mtp.core.exception.MtpEntityMd5EncodeException;
import com.memorycat.notifier.mtp.core.exception.MtpEntityMd5VerifyException;

public class MtpEntityMd5Coder {
	private static final Logger logger = LoggerFactory.getLogger(MtpEntityMd5Coder.class);

	public static MtpEntity encode(MtpEntity mtpEntity) throws MtpEntityMd5EncodeException {
		try {
			MtpEntity result = mtpEntity.clone();
			result.setMd5Verification(new byte[Constants.LENGTH_MTPENTITY_MD5VERIFY]);
			byte[] encode = Md5Util.encode(MtpEntitySerializer.serialize(result));
			if (encode == null) {
				throw new NullPointerException();
			}
			if (encode.length != Constants.LENGTH_MTPENTITY_MD5VERIFY) {
				throw new IndexOutOfBoundsException();
			}
			result.setMd5Verification(encode);
			mtpEntity.setMd5Verification(encode);
			return result;
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage(), e);
			throw new MtpEntityMd5EncodeException(mtpEntity, e);
		}
	}

	public static boolean verifyMPushMessageMd5Value(MtpEntity mtpEntity) throws MtpEntityMd5VerifyException {
		try {
			MtpEntity result = mtpEntity.clone();
			byte[] md5Verification = mtpEntity.getMd5Verification();
			result.setMd5Verification(new byte[Constants.LENGTH_MTPENTITY_MD5VERIFY]);
			byte[] encode = Md5Util.encode(MtpEntitySerializer.serialize(result));
			for (int i = 0; i < Constants.LENGTH_MTPENTITY_MD5VERIFY; i++) {
				if (encode[i] != md5Verification[i]) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage(), e);
			throw new MtpEntityMd5VerifyException(mtpEntity, e);
		}
	}

	public static void main(String[] args) throws Exception {
		MtpEntity mtpEntity = new MtpEntity();
		mtpEntity.setVersion((byte) 33);
		mtpEntity.setBody("sbhaha".getBytes());
		mtpEntity.setBodyLenth(6);
		mtpEntity.setSendFrom(SendFrom.SERVER);
		mtpEntity.setMessageType(MessageType.AUTH_LOGIN_RESPONSE);

		System.out.println(mtpEntity);
		MtpEntity encode = MtpEntityMd5Coder.encode(mtpEntity);
		System.out.println(encode);
		// encode.getMd5Verification()[2]=0;//测试
		boolean verifyMPushMessageMd5Value = MtpEntityMd5Coder.verifyMPushMessageMd5Value(encode);
		System.out.println(verifyMPushMessageMd5Value);

	}
}
