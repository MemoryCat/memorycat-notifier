package com.memorycat.notifier.mtp.core.entity.auth;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EncryptionDto implements Serializable {
	private static final long serialVersionUID = 5443402016407710027L;

	private EncryptionType encryptionType = EncryptionType.DH;
	private Map<String, Object> data = new ConcurrentHashMap<>();

	public EncryptionType getEncryptionType() {
		return encryptionType;
	}

	public void setEncryptionType(EncryptionType encryptionType) {
		this.encryptionType = encryptionType;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((encryptionType == null) ? 0 : encryptionType.hashCode());
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
		EncryptionDto other = (EncryptionDto) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (encryptionType != other.encryptionType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EncryptionDto [encryptionType=" + encryptionType + ", data=" + data + "]";
	}

}
