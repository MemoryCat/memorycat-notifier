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
		if (MPushMessageType.AUTH_ENCRYPT_REQUEST == mPushMessageModel.getMessageType()) {
			loginUser.setClientKey(mPushMessageModel.getBody());
			this.sendResponseEntryptment(loginUser, mPushMessageModel, context);
		} else if (null == loginUser.getClientKey() || loginUser.getClientKey().length <= 0) {
			logger.debug("消息丢弃，应先交换加密密钥：" + mPushMessageModel);
			this.sendRetryEncryptment(loginUser, context);
		} else if (valid == false && mPushMessageModel.getMessageType() != MPushMessageType.AUTH_LOGIN_REQUEST) {
			logger.debug("消息丢弃，应先登录用户认证身份：" + mPushMessageModel);
			this.sendRetryAuthentication(mPushMessageModel, context);
		} else if (MPushMessageType.AUTH_LOGIN_REQUEST == mPushMessageModel.getMessageType()) {
			this.handleLoginRequest(mPushMessageModel, context);
		} else {
			if ("hehe".equals(new String((byte[]) mPushMessageModel.getBody()))) {
				this.mPushMessageServer.sendMessage(loginUser, "hahaha");
			}

		}

	}

	private void sendResponseEntryptment(LoginUser loginUser, MPushMessageModel requestMPushMessageModel,
			IoSession context) throws MPushMessageException, IOException {
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.AUTH_ENCRYPT_RESPONSE);
		mPushMessageModel.setResponseSequence(
				this.mPushMessageServer.getServerConfiguration().getResponseSequence().incrementAndGet());
		mPushMessageModel.setBody(loginUser.getPublicKey());
		this.mPushMessageServer.sendMessage(loginUser, mPushMessageModel);
	}

	private void sendRetryEncryptment(LoginUser loginUser, IoSession context)
			throws MPushMessageException, IOException {
		logger.trace("sendRetryEncryptment...");
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.AUTH_ENCRYPT_RESPONSE_RETRY);
		mPushMessageModel.setResponseSequence(
				this.mPushMessageServer.getServerConfiguration().getResponseSequence().incrementAndGet());
		this.mPushMessageServer.sendMessage(loginUser, mPushMessageModel);

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
		mPushMessageModel.setRequestSequence(requestMPushMessageModel.getRequestSequence());
		mPushMessageModel.setResponseSequence(
				this.mPushMessageServer.getServerConfiguration().getResponseSequence().incrementAndGet());
		mPushMessageModel.setBody(loginResult.toByteArray());
		this.mPushMessageServer.sendMessage(loginUser, mPushMessageModel);
	}

	private void sendRetryAuthentication(MPushMessageModel requestMPushMessageModel, IoSession ioSession)
			throws MPushMessageException, IOException {
		logger.trace("sending:" + MPushMessageType.AUTH_LOGIN_RESPONSE_RETRY.name());
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.AUTH_LOGIN_RESPONSE_RETRY);
		mPushMessageModel.setRequestSequence(requestMPushMessageModel.getRequestSequence());
		mPushMessageModel.setResponseSequence(
				this.mPushMessageServer.getServerConfiguration().getResponseSequence().incrementAndGet());
		this.mPushMessageServer.sendMessage(
				this.mPushMessageServer.getServerConfiguration().getLoginUserManager().getLoginUser(ioSession),
				mPushMessageModel);
	};
}
