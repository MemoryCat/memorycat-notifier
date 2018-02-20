package com.memorycat.module.notifier.mpush.client.model;

import java.io.Serializable;
import java.security.KeyPair;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.memorycat.module.notifier.mpush.auth.Authenticator;
import com.memorycat.module.notifier.util.KeyUtil;

public class ClientUser implements Serializable {

	private static final long serialVersionUID = 4675322149657022508L;

	private final byte[] publicKey;
	private final byte[] privateKey;
	private byte[] serverKey;
	private Authenticator authenticator;
	// private Object serverAuthentication;
	private Date loginDate = new Date();
	private final Map<String, Object> extendProperties = new ConcurrentHashMap<>();

	public ClientUser() {
		KeyPair keyPair = KeyUtil.getKeyPair();
		this.publicKey = keyPair.getPublic().getEncoded();
		this.privateKey = keyPair.getPrivate().getEncoded();
	}

	public byte[] getPublicKey() {
		return publicKey;
	}

	public byte[] getPrivateKey() {
		return privateKey;
	}

	public byte[] getServerKey() {
		return serverKey;
	}

	public void setServerKey(byte[] serverKey) {
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
		result = prime * result + Arrays.hashCode(privateKey);
		result = prime * result + Arrays.hashCode(publicKey);
		result = prime * result + Arrays.hashCode(serverKey);
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
		if (!Arrays.equals(privateKey, other.privateKey))
			return false;
		if (!Arrays.equals(publicKey, other.publicKey))
			return false;
		if (!Arrays.equals(serverKey, other.serverKey))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClientUser [publicKey=" + Arrays.toString(publicKey) + ", privateKey=" + Arrays.toString(privateKey)
				+ ", serverKey=" + Arrays.toString(serverKey) + ", authenticator=" + authenticator + ", loginDate="
				+ loginDate + ", extendProperties=" + extendProperties + "]";
	}

}
