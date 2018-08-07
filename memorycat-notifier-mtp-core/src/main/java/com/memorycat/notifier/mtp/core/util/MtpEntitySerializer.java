package com.memorycat.notifier.mtp.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.notifier.mtp.core.entity.MessageType;
import com.memorycat.notifier.mtp.core.entity.MtpEntity;
import com.memorycat.notifier.mtp.core.entity.SendFrom;
import com.memorycat.notifier.mtp.core.exception.MtpEntitySerializeException;
import com.memorycat.notifier.mtp.core.exception.MtpEntityUnSerializeException;

public class MtpEntitySerializer {
	private static final Logger logger = LoggerFactory.getLogger(MtpEntitySerializer.class);

	public static byte[] serialize(MtpEntity mtpEntity) throws MtpEntitySerializeException {
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(byteArrayOutputStream);
			out.write(mtpEntity.getPrefix().getBytes());
			out.writeByte(mtpEntity.getVersion());
			out.writeByte(mtpEntity.getSendFrom().getSide());
			out.writeShort(mtpEntity.getMessageType().getTypeCode());
			out.writeLong(mtpEntity.getTimeStmap());
			out.write(mtpEntity.getUuid().getBytes(), 0, Constants.LENGTH_MTPENTITY_UUID);
			out.writeInt(mtpEntity.getBodyLenth());
			out.write(mtpEntity.getMd5Verification(), 0, Constants.LENGTH_MTPENTITY_MD5VERIFY);
			out.write(mtpEntity.getBody(), 0, mtpEntity.getBodyLenth());
			out.close();
			return byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage(), e);
			throw new MtpEntitySerializeException(mtpEntity, e);
		}
	}

	public static MtpEntity unserialize(byte[] data) throws MtpEntityUnSerializeException {
		MtpEntity mtpEntity = new MtpEntity();
		try {
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));

			byte[] bufMark = new byte[3];
			in.read(bufMark, 0, 3);
			if (!new String(bufMark).equals(Constants.MESSAGE_MARK)) {
				Exception e = new MtpEntityUnSerializeException(mtpEntity,
						Constants.EXCEPTION_MTPENTITY_MESSAGE_MARK_NOT_MATCH);
				logger.warn(e.getLocalizedMessage(), e);
				throw e;
			}

			mtpEntity.setVersion(in.readByte());
			mtpEntity.setSendFrom(SendFrom.valueOf(in.readByte()));
			mtpEntity.setMessageType(MessageType.valueOf(in.readShort()));
			mtpEntity.setTimeStmap(in.readLong());
			byte[] bufUuid = new byte[Constants.LENGTH_MTPENTITY_UUID];
			in.read(bufUuid, 0, Constants.LENGTH_MTPENTITY_UUID);
			mtpEntity.setUuid(new String(bufUuid));
			mtpEntity.setBodyLenth(in.readInt());

			byte[] bufMd5 = new byte[Constants.LENGTH_MTPENTITY_MD5VERIFY];
			in.read(bufMd5, 0, Constants.LENGTH_MTPENTITY_MD5VERIFY);
			mtpEntity.setMd5Verification(bufMd5);

			if (mtpEntity.getBodyLenth() < 0) {
				Exception e = new IndexOutOfBoundsException("" + mtpEntity.getBodyLenth());
				logger.warn(e.getLocalizedMessage(), e);
				throw e;
			}
			byte[] bufBody = new byte[mtpEntity.getBodyLenth()];
			in.read(bufBody, 0, mtpEntity.getBodyLenth());
			mtpEntity.setBody(bufBody);

			in.close();
			return mtpEntity;
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage(), e);
			throw new MtpEntityUnSerializeException(mtpEntity, e);
		}
	}

	/**
	 * 获取一个MtpEntity至少需要多少个字节长度
	 * 
	 * @return 失败返回-1，成功返回实际值( 大于0)
	 */
	public static int getMtpEntityMiniousByteSize() {
		try {
			MtpEntity mtpEntity = new MtpEntity();
			byte[] serialize = serialize(mtpEntity);
			return serialize.length;
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage(), e);
			return -1;
		}
	}

	public static void main(String[] args)
			throws IOException, MtpEntitySerializeException, MtpEntityUnSerializeException {
//		File file = new File("c:/1.bin");
//		FileOutputStream fileOutputStream = new FileOutputStream(file);
//		MtpEntity mtpEntity = new MtpEntity();
//		mtpEntity.setVersion((byte) 33);
//		mtpEntity.setBody("sbhaha".getBytes());
//		mtpEntity.setBodyLenth(6);
//		mtpEntity.setSendFrom(SendFrom.SERVER);
//		mtpEntity.setMessageType(MessageType.AUTH_LOGIN_RESPONSE);
//		fileOutputStream.write(serialize(mtpEntity));
//		fileOutputStream.close();
//
//		FileInputStream fileInputStream = new FileInputStream(file);
//		byte[] buf = new byte[(int) file.length()];
//		fileInputStream.read(buf);
//		MtpEntity mtpEntity2 = unserialize(buf);
//
//		System.out.println(mtpEntity2);
//
//		fileInputStream.close();
		
		
		System.out.println( getMtpEntityMiniousByteSize());

	}

}
