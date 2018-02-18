package com.memorycat.module.notifier.mpush.client.config;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.mina.core.session.IoSession;

import com.memorycat.module.notifier.mpush.auth.Authenticator;
import com.memorycat.module.notifier.mpush.client.MPushMessageClient;
import com.memorycat.module.notifier.mpush.server.model.LoginUser;

public class ClientConfiguration {
	private MPushMessageClient mPushMessageClient;
	private LoginUser loginUser;
	private IoSession ioSession;
	private Authenticator authenticator;
	private AtomicLong requestSequence = new AtomicLong(1L);
	private AtomicLong responseSequence = new AtomicLong(1L);

	public MPushMessageClient getmPushMessageClient() {
		return mPushMessageClient;
	}

	public void setmPushMessageClient(MPushMessageClient mPushMessageClient) {
		this.mPushMessageClient = mPushMessageClient;
	}

	public LoginUser getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(LoginUser loginUser) {
		this.loginUser = loginUser;
	}

	public IoSession getIoSession() {
		return ioSession;
	}

	public void setIoSession(IoSession ioSession) {
		this.ioSession = ioSession;
	}

	public Authenticator getAuthenticator() {
		return authenticator;
	}

	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
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

}
