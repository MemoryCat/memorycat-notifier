package com.memorycat.module.notifier.mpush.server.config;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import com.memorycat.module.notifier.mpush.auth.LoginUserAuthenticator;
import com.memorycat.module.notifier.mpush.server.MPushMessageServer;
import com.memorycat.module.notifier.mpush.server.auth.AuthenticatorServerManger;
import com.memorycat.module.notifier.mpush.server.listener.MPushServerListener;

public class ServerConfiguration {

	private MPushMessageServer mPushMessageServer;
	private LoginUserManager loginUserManager = new LoginUserManager();
	private AuthenticatorServerManger authenticatorServerManger;
	private LoginUserAuthenticator loginUserAuthenticator;
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

}
