package com.memorycat.module.notifier.mpush.client.model;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.memorycat.module.notifier.mpush.auth.Authenticator;
import com.memorycat.module.notifier.util.EncryptUtil;

public class ClientUser implements Serializable {

	private static final long serialVersionUID = 4675322149657022508L;

	private final PublicKey publicKey;
	private final PrivateKey privateKey;
	private PublicKey serverKey;
	private Authenticator authenticator;
	// private Object serverAuthentication;
	private Date loginDate = new Date();
	private final Map<String, Object> extendProperties = new ConcurrentHashMap<>();

	public ClientUser() {
		KeyPair keyPair = EncryptUtil.getKeyPair();
		this.publicKey = keyPair.getPublic();
		this.privateKey = keyPair.getPrivate();
	}

	public PublicKey getServerKey() {
		return serverKey;
	}

	public void setServerKey(PublicKey serverKey) {
		this.serverKey = serverKey;
	}

	public Authenticator getAuthenticator() {
		return authenticator;
	}

	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
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
		result = prime * result + ((privateKey == null) ? 0 : privateKey.hashCode());
		result = prime * result + ((publicKey == null) ? 0 : publicKey.hashCode());
		result = prime * result + ((serverKey == null) ? 0 : serverKey.hashCode());
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
		ClientUser other = (ClientUser) obj;
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
		if (privateKey == null) {
			if (other.privateKey != null)
				return false;
		} else if (!privateKey.equals(other.privateKey))
			return false;
		if (publicKey == null) {
			if (other.publicKey != null)
				return false;
		} else if (!publicKey.equals(other.publicKey))
			return false;
		if (serverKey == null) {
			if (other.serverKey != null)
				return false;
		} else if (!serverKey.equals(other.serverKey))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClientUser [publicKey=" + publicKey + ", privateKey=" + privateKey + ", serverKey=" + serverKey
				+ ", authenticator=" + authenticator + ", loginDate=" + loginDate + ", extendProperties="
				+ extendProperties + "]";
	}

}
