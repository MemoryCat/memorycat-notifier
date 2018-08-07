package com.memorycat.notifier.mtp.client.auth;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class User {

	private Authenticator authenticator;
	private UserDataEncryption userDataEncryption;
	private Date loginDate = new Date(0L);
	private final Map<String, Object> extendProperties = new ConcurrentHashMap<>();

	public Authenticator getAuthenticator() {
		return authenticator;
	}

	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	public UserDataEncryption getUserDataEncryption() {
		return userDataEncryption;
	}

	public void setUserDataEncryption(UserDataEncryption userDataEncryption) {
		this.userDataEncryption = userDataEncryption;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Map<String, Object> getExtendProperties() {
		return extendProperties;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authenticator == null) ? 0 : authenticator.hashCode());
		result = prime * result + ((extendProperties == null) ? 0 : extendProperties.hashCode());
		result = prime * result + ((loginDate == null) ? 0 : loginDate.hashCode());
		result = prime * result + ((userDataEncryption == null) ? 0 : userDataEncryption.hashCode());
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
		User other = (User) obj;
		if (authenticator == null) {
			if (other.authenticator != null)
				return false;
		} else if (!authenticator.equals(other.authenticator))
			return false;
		if (extendProperties == null) {
			if (other.extendProperties != null)
				return false;
		} else if (!extendProperties.equals(other.extendProperties))
			return false;
		if (loginDate == null) {
			if (other.loginDate != null)
				return false;
		} else if (!loginDate.equals(other.loginDate))
			return false;
		if (userDataEncryption == null) {
			if (other.userDataEncryption != null)
				return false;
		} else if (!userDataEncryption.equals(other.userDataEncryption))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [authenticator=" + authenticator + ", userDataEncryption=" + userDataEncryption + ", loginDate="
				+ loginDate + ", extendProperties=" + extendProperties + "]";
	}

}
