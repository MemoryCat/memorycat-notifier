package com.memorycat.module.notifier.mpush.server.model;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.memorycat.module.notifier.util.KeyUtil;

/**
 * 登录的用户包含：
 * 
 * 1.公钥和私钥和客户端密钥(用户dh密匙交换，加密消息用的)
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
	 * 公钥
	 */
	private final PublicKey publicKey;
	/**
	 * 私钥
	 */
	private final PrivateKey privateKey;

	/**
	 * 客户端的密钥
	 */
	private byte[] clientKey;

	/**
	 * 链接地址信息
	 */
	private ConnectionAddress connectionAddress;
	/**
	 * 认证信息
	 */
	private Object authentication;

	/**
	 * 用户。该字段可指向{@link com.memorycat.module.notifier.db.model.NotificationMessage#user}
	 */
	private String userId;
	/**
	 * 登录时间
	 */
	private Date loginTime = new Date();

	/**
	 * 最后一次心跳包时间
	 */
	private Date lastHeartBeat = new Date(0L);
	/**
	 * 登录设备
	 */
	private String loginDevice;
	/**
	 * 其余一些杂七杂八的扩展属性
	 */
	private final Map<String, Object> extendProperties = new ConcurrentHashMap<>();

	public LoginUser() {
		KeyPair keyPair = KeyUtil.getKeyPair();
		this.publicKey = keyPair.getPublic();
		this.privateKey = keyPair.getPrivate();

	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
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

	public byte[] getClientKey() {
		return clientKey;
	}

	public void setClientKey(byte[] clientKey) {
		this.clientKey = clientKey;
	}

	public Date getLastHeartBeat() {
		return lastHeartBeat;
	}

	public void setLastHeartBeat(Date lastHeartBeat) {
		this.lastHeartBeat = lastHeartBeat;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authentication == null) ? 0 : authentication.hashCode());
		result = prime * result + Arrays.hashCode(clientKey);
		result = prime * result + ((connectionAddress == null) ? 0 : connectionAddress.hashCode());
		result = prime * result + ((extendProperties == null) ? 0 : extendProperties.hashCode());
		result = prime * result + ((lastHeartBeat == null) ? 0 : lastHeartBeat.hashCode());
		result = prime * result + ((loginDevice == null) ? 0 : loginDevice.hashCode());
		result = prime * result + ((loginTime == null) ? 0 : loginTime.hashCode());
		result = prime * result + ((privateKey == null) ? 0 : privateKey.hashCode());
		result = prime * result + ((publicKey == null) ? 0 : publicKey.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		if (!Arrays.equals(clientKey, other.clientKey))
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
		if (lastHeartBeat == null) {
			if (other.lastHeartBeat != null)
				return false;
		} else if (!lastHeartBeat.equals(other.lastHeartBeat))
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
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LoginUser [ connectionAddress=" + connectionAddress + ", authentication=" + authentication + ", userId="
				+ userId + ", loginTime=" + loginTime + ", lastHeartBeat=" + lastHeartBeat + ", loginDevice="
				+ loginDevice + ", extendProperties=" + extendProperties + "]";
	}

}
