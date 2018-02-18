package com.memorycat.module.notifier.mpush.server.config;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.memorycat.module.notifier.mpush.auth.LoginUserAuthenticator;
import com.memorycat.module.notifier.mpush.listener.MPushMessageListener;
import com.memorycat.module.notifier.mpush.server.MPushMessageServer;
import com.memorycat.module.notifier.mpush.server.auth.AuthenticatorServerManger;

public class ServerConfiguration {

	private MPushMessageServer mPushMessageServer;
	private LoginUserManager loginUserManager = new LoginUserManager();
	private AuthenticatorServerManger authenticatorServerManger;
	private LoginUserAuthenticator loginUserAuthenticator;
	private AtomicLong requestSequence = new AtomicLong(1L);
	private AtomicLong responseSequence = new AtomicLong(1L);
	private List<MPushMessageListener> listeners = new LinkedList<>();

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

	public void setRequestSequence(AtomicLong requestSequence) {
		this.requestSequence = requestSequence;
	}

	public AtomicLong getResponseSequence() {
		return responseSequence;
	}

	public void setResponseSequence(AtomicLong responseSequence) {
		this.responseSequence = responseSequence;
	}

	public List<MPushMessageListener> getListeners() {
		return listeners;
	}

	public void setListeners(List<MPushMessageListener> listeners) {
		this.listeners = listeners;
	}

}
