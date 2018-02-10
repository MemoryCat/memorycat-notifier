package com.memorycat.module.notifier.mpush.model;

import java.io.Serializable;
import java.util.Arrays;

import com.memorycat.module.notifier.util.MPushMessageModelConstants;

public class MPushMessageModel implements Serializable, Cloneable {

	private static final long serialVersionUID = 5431068199966868908L;

	private final String prefix = MPushMessageModelConstants.MESSAGE_MARK;
	private byte version = 1;
	// private short messageType;
	private MPushMessageType messageType = MPushMessageType.UNKOWN;
	private long timeStmap = System.currentTimeMillis();
	private long requestSequence = 0;
	private long responseSequence = 0;
	private short bodyLenth;
	/**
	 * 该值=前面的header+md5字段填充0+body
	 */
	private byte[] md5Verification = new byte[16];
	private byte[] body = new byte[0]; // 最大402

	@Override
	public MPushMessageModel clone() throws CloneNotSupportedException {
		return (MPushMessageModel) super.clone();
	}

	public byte getVersion() {
		return version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	public MPushMessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MPushMessageType messageType) {
		this.messageType = messageType;
	}

	public long getTimeStmap() {
		return timeStmap;
	}

	public void setTimeStmap(long timeStmap) {
		this.timeStmap = timeStmap;
	}

	public long getRequestSequence() {
		return requestSequence;
	}

	public void setRequestSequence(long requestSequence) {
		this.requestSequence = requestSequence;
	}

	public long getResponseSequence() {
		return responseSequence;
	}

	public void setResponseSequence(long responseSequence) {
		this.responseSequence = responseSequence;
	}

	public short getBodyLenth() {
		return bodyLenth;
	}

	public void setBodyLenth(short bodyLenth) {
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
		this.body = body;
	}

	public String getPrefix() {
		return prefix;
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
		result = prime * result + (int) (requestSequence ^ (requestSequence >>> 32));
		result = prime * result + (int) (responseSequence ^ (responseSequence >>> 32));
		result = prime * result + (int) (timeStmap ^ (timeStmap >>> 32));
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
		MPushMessageModel other = (MPushMessageModel) obj;
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
		if (requestSequence != other.requestSequence)
			return false;
		if (responseSequence != other.responseSequence)
			return false;
		if (timeStmap != other.timeStmap)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MPushMessageModel [prefix=" + prefix + ", version=" + version + ", messageType=" + messageType
				+ ", timeStmap=" + timeStmap + ", requestSequence=" + requestSequence + ", responseSequence="
				+ responseSequence + ", bodyLenth=" + bodyLenth + ", md5Verification="
				+ Arrays.toString(md5Verification) + ", body=" + Arrays.toString(body) + "]";
	}

}
