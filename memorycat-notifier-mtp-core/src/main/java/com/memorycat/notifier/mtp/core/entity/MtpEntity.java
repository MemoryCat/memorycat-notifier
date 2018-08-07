package com.memorycat.notifier.mtp.core.entity;

import java.util.Arrays;

import com.memorycat.notifier.mtp.core.util.Constants;
import com.memorycat.notifier.mtp.core.util.UuidUtil;

public class MtpEntity implements Cloneable {

	private final String prefix = Constants.MESSAGE_MARK;
	private byte version = 1;
	private SendFrom sendFrom = SendFrom.UNKNOWN;
	// private short messageType;
	private MessageType messageType = MessageType.UNKOWN;
	private long timeStmap = System.currentTimeMillis();
	private String uuid = UuidUtil.generateUUID();
	private int bodyLenth;

	/**
	 * 该值=前面的header+md5字段填充0+body
	 */
	private byte[] md5Verification = new byte[Constants.LENGTH_MTPENTITY_MD5VERIFY];
	private byte[] body = new byte[0];

	
	
	
	@Override
	public MtpEntity clone() throws CloneNotSupportedException {
		return (MtpEntity) super.clone();
	}

	public byte getVersion() {
		return version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	public SendFrom getSendFrom() {
		return sendFrom;
	}

	public void setSendFrom(SendFrom sendFrom) {
		this.sendFrom = sendFrom;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public long getTimeStmap() {
		return timeStmap;
	}

	public void setTimeStmap(long timeStmap) {
		this.timeStmap = timeStmap;
	}

	public int getBodyLenth() {
		return bodyLenth;
	}

	public void setBodyLenth(int bodyLenth) {
		this.bodyLenth = bodyLenth;
	}

	public byte[] getMd5Verification() {
		return md5Verification;
	}

	public void setMd5Verification(byte[] md5Verification) {
		this.md5Verification = md5Verification;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		if (body == null) {
			throw new NullPointerException();
		}
		this.body = body;

	}

	public String getPrefix() {
		return prefix;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(body);
		result = prime * result + bodyLenth;
		result = prime * result + Arrays.hashCode(md5Verification);
		result = prime * result + ((messageType == null) ? 0 : messageType.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result + ((sendFrom == null) ? 0 : sendFrom.hashCode());
		result = prime * result + (int) (timeStmap ^ (timeStmap >>> 32));
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MtpEntity other = (MtpEntity) obj;
		if (!Arrays.equals(body, other.body))
			return false;
		if (bodyLenth != other.bodyLenth)
			return false;
		if (!Arrays.equals(md5Verification, other.md5Verification))
			return false;
		if (messageType != other.messageType)
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (sendFrom != other.sendFrom)
			return false;
		if (timeStmap != other.timeStmap)
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MtpEntity [prefix=" + prefix + ", version=" + version + ", sendFrom=" + sendFrom + ", messageType="
				+ messageType + ", timeStmap=" + timeStmap + ", uuid=" + uuid + ", bodyLenth=" + bodyLenth
				+ ", md5Verification=" + Arrays.toString(md5Verification) + ", body=" + Arrays.toString(body) + "]";
	}

}
