package com.memorycat.notifier.mtp.core.impl;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.notifier.mtp.core.entity.MtpEntity;
import com.memorycat.notifier.mtp.core.exception.MtpDecodeException;
import com.memorycat.notifier.mtp.core.util.MtpEntityMd5Coder;
import com.memorycat.notifier.mtp.core.util.MtpEntitySerializer;

public class MemoryCatTransferProtocolDecoder extends CumulativeProtocolDecoder {
	private static final Logger logger = LoggerFactory.getLogger(MemoryCatTransferProtocolDecoder.class);
	private static final int MTP_MINISIZE = MtpEntitySerializer.getMtpEntityMiniousByteSize();

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		int limit = in.limit();
		if (limit < MTP_MINISIZE) {
			in.flip();
			return false;
		}
		byte[] buf = new byte[limit];
		in.get(buf);
		MtpEntity tmpMtpEntity = MtpEntitySerializer.unserialize(buf);

		if (tmpMtpEntity.getBodyLenth() < 0) {
			String msg = "数据包大小值校验不正确，丢弃数据：" + new String(buf);
			logger.warn(msg);
			throw new MtpDecodeException(msg);
		}

		if (limit < MTP_MINISIZE + tmpMtpEntity.getBodyLenth()) {
			in.flip();
			return false;
		} else {

			// 这里重新再次读取，是为了只取这个mtpEntity的getBodyLenth()的数据包大小，超出了的数据包算下一个mtpEntity
			in.flip();
			byte[] data = new byte[MTP_MINISIZE+tmpMtpEntity.getBodyLenth()];
			in.get(data);
			MtpEntity mtpEntity = MtpEntitySerializer.unserialize(data);
			if (MtpEntityMd5Coder.verifyMPushMessageMd5Value(mtpEntity)) {
				out.write(mtpEntity);
				return true;
			} else {
				String msg = "数据包md5校验不正确，丢弃数据：" + new String(buf);
				logger.warn(msg);
				throw new MtpDecodeException(msg);
			}
		}
	}
}
