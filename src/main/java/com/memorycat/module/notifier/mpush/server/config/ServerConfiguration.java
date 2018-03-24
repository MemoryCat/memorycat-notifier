package com.memorycat.module.notifier.mpush.server.config;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import com.memorycat.module.notifier.db.service.NotificationMessageService;
import com.memorycat.module.notifier.mpush.auth.LoginUserAuthenticator;
import com.memorycat.module.notifier.mpush.server.MPushMessageServer;
import com.memorycat.module.notifier.mpush.server.auth.AuthenticatorServerManger;
import com.memorycat.module.notifier.mpush.server.listener.MPushServerListener;
import com.memorycat.module.notifier.mpush.util.Constants;

public class ServerConfiguration {

	private MPushMessageServer mPushMessageServer;
	private LoginUserManager loginUserManager = new LoginUserManager();
	private AuthenticatorServerManger authenticatorServerManger;
	private LoginUserAuthenticator loginUserAuthenticator;
	private NotificationMessageService notificationMessageService;
	/**
	 * 心跳包时间（单位：秒）。
	 * 
	 * 服务端主动去发送客户端心跳包，等待客户端响应心跳包。
	 */
	private int heartBeatSeconds = 30;
	/**
	 * 心跳最大失败次数，如果超过这个次数客户端还没有响应心跳包那么可视为该用户已断线
	 */
	private int heartBeatFailedCount = 5;
	/**
	 * 周期性地发送数据库未发送的消息的间隔时间。单位：毫秒。
	 * 
	 * 
	 * (1秒=1000毫秒)
	 * 
	 */
	private long sendDbMessagePeriod = 1000L;
	private final AtomicLong requestSequence = new AtomicLong(1L);
	private final AtomicLong responseSequence = new AtomicLong(1L);
	private final List<MPushServerListener> listeners = new CopyOnWriteArrayList<MPushServerListener>();
	private final int port;

	public ServerConfiguration(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	public AuthenticatorServerManger getAuthenticatorServerManger() {
		return authenticatorServerManger;
	}

	public void setAuthenticatorServerManger(AuthenticatorServerManger authenticatorServerManger) {
		this.authenticatorServerManger = authenticatorServerManger;
	}

	public MPushMessageServer getmPushMessageServer() {
		return mPushMessageServer;
	}

	public void setmPushMessageServer(MPushMessageServer mPushMessageServer) {
		this.mPushMessageServer = mPushMessageServer;
	}

	public LoginUserManager getLoginUserManager() {
		return loginUserManager;
	}

	public void setLoginUserManager(LoginUserManager loginUserManager) {
		this.loginUserManager = loginUserManager;
	}

	public LoginUserAuthenticator getLoginUserAuthenticator() {
		return loginUserAuthenticator;
	}

	public void setLoginUserAuthenticator(LoginUserAuthenticator loginUserAuthenticator) {
		this.loginUserAuthenticator = loginUserAuthenticator;
	}

	public AtomicLong getRequestSequence() {
		return requestSequence;
	}

	public AtomicLong getResponseSequence() {
		return responseSequence;
	}

	public List<MPushServerListener> getListeners() {
		return listeners;
	}

	public int getHeartBeatSeconds() {
		return heartBeatSeconds;
	}

	public void setHeartBeatSeconds(int heartBeatSeconds) {
		if (heartBeatSeconds <= 0) {
			throw new IllegalArgumentException(Constants.EXCEPTION_ARGUMENTVALUE_SHOULD_GREATER_THAN_ZERO);
		}
		this.heartBeatSeconds = heartBeatSeconds;
	}

	public int getHeartBeatFailedCount() {
		return heartBeatFailedCount;
	}

	public void setHeartBeatFailedCount(int heartBeatFailedCount) {
		if (heartBeatSeconds <= 0) {
			throw new IllegalArgumentException(Constants.EXCEPTION_ARGUMENTVALUE_SHOULD_GREATER_THAN_ZERO);
		}
		this.heartBeatFailedCount = heartBeatFailedCount;
	}

	public long getSendDbMessagePeriod() {
		return sendDbMessagePeriod;
	}

	public void setSendDbMessagePeriod(long sendDbMessagePeriod) {
		this.sendDbMessagePeriod = sendDbMessagePeriod;
	}

	public NotificationMessageService getNotificationMessageService() {
		return notificationMessageService;
	}

	public void setNotificationMessageService(NotificationMessageService notificationMessageService) {
		this.notificationMessageService = notificationMessageService;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authenticatorServerManger == null) ? 0 : authenticatorServerManger.hashCode());
		result = prime * result + heartBeatFailedCount;
		result = prime * result + heartBeatSeconds;
		result = prime * result + ((listeners == null) ? 0 : listeners.hashCode());
		result = prime * result + ((loginUserAuthenticator == null) ? 0 : loginUserAuthenticator.hashCode());
		result = prime * result + ((loginUserManager == null) ? 0 : loginUserManager.hashCode());
		result = prime * result + ((mPushMessageServer == null) ? 0 : mPushMessageServer.hashCode());
		result = prime * result + ((notificationMessageService == null) ? 0 : notificationMessageService.hashCode());
		result = prime * result + port;
		result = prime * result + ((requestSequence == null) ? 0 : requestSequence.hashCode());
		result = prime * result + ((responseSequence == null) ? 0 : responseSequence.hashCode());
		result = prime * result + (int) (sendDbMessagePeriod ^ (sendDbMessagePeriod >>> 32));
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
		ServerConfiguration other = (ServerConfiguration) obj;
		if (authenticatorServerManger == null) {
			if (other.authenticatorServerManger != null)
				return false;
		} else if (!authenticatorServerManger.equals(other.authenticatorServerManger))
			return false;
		if (heartBeatFailedCount != other.heartBeatFailedCount)
			return false;
		if (heartBeatSeconds != other.heartBeatSeconds)
			return false;
		if (listeners == null) {
			if (other.listeners != null)
				return false;
		} else if (!listeners.equals(other.listeners))
			return false;
		if (loginUserAuthenticator == null) {
			if (other.loginUserAuthenticator != null)
				return false;
		} else if (!loginUserAuthenticator.equals(other.loginUserAuthenticator))
			return false;
		if (loginUserManager == null) {
			if (other.loginUserManager != null)
				return false;
		} else if (!loginUserManager.equals(other.loginUserManager))
			return false;
		if (mPushMessageServer == null) {
			if (other.mPushMessageServer != null)
				return false;
		} else if (!mPushMessageServer.equals(other.mPushMessageServer))
			return false;
		if (notificationMessageService == null) {
			if (other.notificationMessageService != null)
				return false;
		} else if (!notificationMessageService.equals(other.notificationMessageService))
			return false;
		if (port != other.port)
			return false;
		if (requestSequence == null) {
			if (other.requestSequence != null)
				return false;
		} else if (!requestSequence.equals(other.requestSequence))
			return false;
		if (responseSequence == null) {
			if (other.responseSequence != null)
				return false;
		} else if (!responseSequence.equals(other.responseSequence))
			return false;
		if (sendDbMessagePeriod != other.sendDbMessagePeriod)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ServerConfiguration [mPushMessageServer=" + mPushMessageServer + ", loginUserManager="
				+ loginUserManager + ", authenticatorServerManger=" + authenticatorServerManger
				+ ", loginUserAuthenticator=" + loginUserAuthenticator + ", notificationMessageService="
				+ notificationMessageService + ", heartBeatSeconds=" + heartBeatSeconds + ", heartBeatFailedCount="
				+ heartBeatFailedCount + ", sendDbMessagePeriod=" + sendDbMessagePeriod + ", requestSequence="
				+ requestSequence + ", responseSequence=" + responseSequence + ", listeners=" + listeners + ", port="
				+ port + "]";
	}

}
