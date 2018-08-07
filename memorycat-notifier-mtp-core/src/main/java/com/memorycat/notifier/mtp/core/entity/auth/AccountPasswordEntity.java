package com.memorycat.notifier.mtp.core.entity.auth;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountPasswordEntity implements Serializable {

	private static final long serialVersionUID = -8080802500861517477L;
	protected ClientDeviceType clientDeviceType = ClientDeviceType.UNKNOWN;
	protected String account;
	protected String password;
	protected Map<String, Object> extendProperties = new ConcurrentHashMap<>();

	public AccountPasswordEntity(ClientDeviceType clientDeviceType, String account, String password) {
		super();
		this.clientDeviceType = clientDeviceType;
		this.account = account;
		this.password = password;
	}

	public AccountPasswordEntity(String account, String password) {
		super();
		this.account = account;
		this.password = password;
	}

	public AccountPasswordEntity() {
		super();
	}

	public ClientDeviceType getClientDeviceType() {
		return clientDeviceType;
	}

	public void setClientDeviceType(ClientDeviceType clientDeviceType) {
		this.clientDeviceType = clientDeviceType;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<String, Object> getExtendProperties() {
		return extendProperties;
	}

	public void setExtendProperties(Map<String, Object> extendProperties) {
		this.extendProperties = extendProperties;
	}

	@Override
	public String toString() {
		return "AccountPasswordEntity [clientDeviceType=" + clientDeviceType + ", account=" + account + ", password="
				+ password + ", extendProperties=" + extendProperties + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((clientDeviceType == null) ? 0 : clientDeviceType.hashCode());
		result = prime * result + ((extendProperties == null) ? 0 : extendProperties.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		AccountPasswordEntity other = (AccountPasswordEntity) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (clientDeviceType != other.clientDeviceType)
			return false;
		if (extendProperties == null) {
			if (other.extendProperties != null)
				return false;
		} else if (!extendProperties.equals(other.extendProperties))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}
