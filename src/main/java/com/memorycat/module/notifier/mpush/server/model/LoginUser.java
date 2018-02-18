package com.memorycat.module.notifier.mpush.server.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 登录的用户包含：
 * 
 * 1.公钥和私钥(用户dh密匙交换，加密消息用的)
 * 
 * 2.链接地址（{@link com.memorycat.module.notifier.mpush.server.model.ConnectionAddress}）
 * 
 * 3.用户登录认证信息。
 * 
 * 4.其余一些杂七杂八的属性
 * 
 * @author xie
 *
 */
public class LoginUser implements Serializable {

	private static final long serialVersionUID = -5376017400205035550L;

	/**
	 * 私钥
	 */
	private byte[] privateKey;
	/**
	 * 公钥
	 */
	private byte[] publicKey;

	/**
	 * 链接地址信息
	 */
	private ConnectionAddress connectionAddress;
	/**
	 * 认证信息
	 */
	private Object authentication;

	/**
	 * 登录时间
	 */
	private Date loginTime = new Date();
	/**
	 * 登录设备
	 */
	private String loginDevice;
	/**
	 * 其余一些杂七杂八的扩展属性
	 */
	private final Map<String, Object> extendProperties = new ConcurrentHashMap<>();

	public byte[] getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(byte[] privateKey) {
		this.privateKey = privateKey;
	}

	public byte[] getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(byte[] publicKey) {
		this.publicKey = publicKey;
	}

	public ConnectionAddress getConnectionAddress() {
		return connectionAddress;
	}

	public void setConnectionAddress(ConnectionAddress connectionAddress) {
		this.connectionAddress = connectionAddress;
	}

	public Object getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Object authentication) {
		this.authentication = authentication;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginDevice() {
		return loginDevice;
	}

	public void setLoginDevice(String loginDevice) {
		this.loginDevice = loginDevice;
	}

	public Map<String, Object> getExtendProperties() {
		return extendProperties;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authentication == null) ? 0 : authentication.hashCode());
		result = prime * result + ((connectionAddress == null) ? 0 : connectionAddress.hashCode());
		result = prime * result + ((extendProperties == null) ? 0 : extendProperties.hashCode());
		result = prime * result + ((loginDevice == null) ? 0 : loginDevice.hashCode());
		result = prime * result + ((loginTime == null) ? 0 : loginTime.hashCode());
		result = prime * result + Arrays.hashCode(privateKey);
		result = prime * result + Arrays.hashCode(publicKey);
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
		LoginUser other = (LoginUser) obj;
		if (authentication == null) {
			if (other.authentication != null)
				return false;
		} else if (!authentication.equals(other.authentication))
			return false;
		if (connectionAddress == null) {
			if (other.connectionAddress != null)
				return false;
		} else if (!connectionAddress.equals(other.connectionAddress))
			return false;
		if (extendProperties == null) {
			if (other.extendProperties != null)
				return false;
		} else if (!extendProperties.equals(other.extendProperties))
			return false;
		if (loginDevice == null) {
			if (other.loginDevice != null)
				return false;
		} else if (!loginDevice.equals(other.loginDevice))
			return false;
		if (loginTime == null) {
			if (other.loginTime != null)
				return false;
		} else if (!loginTime.equals(other.loginTime))
			return false;
		if (!Arrays.equals(privateKey, other.privateKey))
			return false;
		if (!Arrays.equals(publicKey, other.publicKey))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LoginUser [privateKey=" + Arrays.toString(privateKey) + ", publicKey=" + Arrays.toString(publicKey)
				+ ", connectionAddress=" + connectionAddress + ", authentication=" + authentication + ", loginTime="
				+ loginTime + ", loginDevice=" + loginDevice + ", extendProperties=" + extendProperties + "]";
	}

}
