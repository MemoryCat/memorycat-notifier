package com.memorycat.module.notifier.mpush.server;

import java.io.IOException;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.module.notifier.mpush.auth.AuthenticatedResult;
import com.memorycat.module.notifier.mpush.exception.MPushMessageException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.model.MPushMessageType;
import com.memorycat.module.notifier.mpush.server.model.LoginUser;

public class MPushMessageServerMessageDisptcher {

	private static final Logger logger = LoggerFactory.getLogger(MPushMessageServerMessageDisptcher.class);

	private final MPushMessageServer mPushMessageServer;

	public MPushMessageServerMessageDisptcher(MPushMessageServer mPushMessageServer) {
		super();
		this.mPushMessageServer = mPushMessageServer;
	}

	public void disptach(MPushMessageModel mPushMessageModel, IoSession context)
			throws MPushMessageException, IOException {
		logger.trace("disptching:" + mPushMessageModel);
		LoginUser loginUser = this.mPushMessageServer.getServerConfiguration().getLoginUserManager()
				.getLoginUser(context);
		boolean valid = this.mPushMessageServer.getServerConfiguration().getLoginUserAuthenticator().isValid(loginUser);
		if (valid == false && mPushMessageModel.getMessageType() != MPushMessageType.AUTH_LOGIN_REQUEST) {
			this.sendAuthention(context);
		} else {
			MPushMessageType messageType = mPushMessageModel.getMessageType();
			switch (messageType) {
			case AUTH_LOGIN_REQUEST:
				this.handleLoginRequest(mPushMessageModel, context);
			}
			// TODO
			// if (this.listeners != null && this.listeners.size() > 0) {
			// for (MPushMessageListener mPushMessageListener : listeners) {
			// }
			// }
		}

	}

	private void handleLoginRequest(MPushMessageModel mPushMessageModel, IoSession context)
			throws MPushMessageException, IOException {
		logger.trace("trying to login...");
		byte[] body = mPushMessageModel.getBody();
		AuthenticatedResult loginResult = this.mPushMessageServer.getServerConfiguration()
				.getAuthenticatorServerManger().login(body);
		LoginUser loginUser = this.mPushMessageServer.getServerConfiguration().getLoginUserManager()
				.getLoginUser(context);
		if (loginResult != null) {
			loginUser.setAuthentication(loginResult);
			// 登录成功
			this.sendLoginSuccessfully(loginUser, loginResult, mPushMessageModel);
		} else {
			// 登录失败
			this.sendLoginUnsuccessfully(loginUser, mPushMessageModel);
		}
	}

	private void sendLoginUnsuccessfully(LoginUser loginUser, MPushMessageModel requestMPushMessageModel)
			throws MPushMessageException, IOException {

		logger.trace("sendLoginUnsuccessfully...");
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.AUTH_LOGIN_RESPONSE_NO);
		mPushMessageModel.setRequestSequence(
				this.mPushMessageServer.getServerConfiguration().getRequestSequence().getAndIncrement());
		mPushMessageModel.setResponseSequence(requestMPushMessageModel.getRequestSequence());
		this.mPushMessageServer.sendMessage(loginUser, mPushMessageModel);
	}

	private void sendLoginSuccessfully(LoginUser loginUser, AuthenticatedResult loginResult,
			MPushMessageModel requestMPushMessageModel) throws MPushMessageException, IOException {

		logger.trace("sendLoginSuccessfully...");
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.AUTH_LOGIN_RESPONSE_OK);
		mPushMessageModel.setRequestSequence(
				this.mPushMessageServer.getServerConfiguration().getRequestSequence().getAndIncrement());
		mPushMessageModel.setResponseSequence(requestMPushMessageModel.getRequestSequence());
		mPushMessageModel.setBody(loginResult.toByteArray());
		this.mPushMessageServer.sendMessage(loginUser, mPushMessageModel);
	}

	private void sendAuthention(IoSession ioSession) throws MPushMessageException, IOException {
		logger.trace("sending:" + MPushMessageType.AUTH_LOGIN_RESPONSE_RETRY.name());
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.AUTH_LOGIN_RESPONSE_RETRY);
		this.mPushMessageServer.sendMessage(
				this.mPushMessageServer.getServerConfiguration().getLoginUserManager().getLoginUser(ioSession),
				mPushMessageModel);
	};
}
