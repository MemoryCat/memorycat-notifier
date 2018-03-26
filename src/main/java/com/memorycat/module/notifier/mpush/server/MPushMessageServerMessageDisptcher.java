package com.memorycat.module.notifier.mpush.server;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.module.notifier.db.model.NotificationMessage;
import com.memorycat.module.notifier.db.service.NotificationMessageService;
import com.memorycat.module.notifier.db.service.impl.NotificationMessageServiceImpl;
import com.memorycat.module.notifier.mpush.auth.AuthenticatedResult;
import com.memorycat.module.notifier.mpush.exception.MPushMessageException;
import com.memorycat.module.notifier.mpush.model.MPushMessageModel;
import com.memorycat.module.notifier.mpush.model.MPushMessageType;
import com.memorycat.module.notifier.mpush.server.auth.ServerAuthenticatedResult;
import com.memorycat.module.notifier.mpush.server.config.ServerConfiguration;
import com.memorycat.module.notifier.mpush.server.model.LoginUser;
import com.memorycat.module.notifier.util.EncryptUtil;

public class MPushMessageServerMessageDisptcher {

	private static final Logger logger = LoggerFactory.getLogger(MPushMessageServerMessageDisptcher.class);

	private final MPushMessageServer mPushMessageServer;
	private final Timer timer = new Timer(true);

	public MPushMessageServerMessageDisptcher(MPushMessageServer mPushMessageServer) {
		super();
		this.mPushMessageServer = mPushMessageServer;
		// this.executorService.execute(new
		// ServerHeartBeatRunnable(this.mPushMessageServer.getServerConfiguration()));
		ServerConfiguration serverConfiguration = this.mPushMessageServer.getServerConfiguration();
		// timer.schedule(new ServerHeartBeatRunnable(serverConfiguration),
		// 1000L * serverConfiguration.getHeartBeatSeconds(), 1000L *
		// serverConfiguration.getHeartBeatSeconds());

		if (serverConfiguration.getNotificationMessageService() == null) {
			serverConfiguration.setNotificationMessageService(new NotificationMessageServiceImpl());
		}
		timer.schedule(new ServerDatabaseMessageRunnable(serverConfiguration),
				serverConfiguration.getSendDbMessagePeriod(), serverConfiguration.getSendDbMessagePeriod());
	}

	public void disptach(MPushMessageModel mPushMessageModel, IoSession context)
			throws MPushMessageException, IOException, Exception {
		logger.trace("disptching:" + mPushMessageModel);
		LoginUser loginUser = this.mPushMessageServer.getServerConfiguration().getLoginUserManager()
				.getLoginUser(context);
		if (mPushMessageModel.getMessageType() == MPushMessageType.AUTH_ENCRYPT_REQUEST) {
//			loginUser.setClientKey(mPushMessageModel.getBody());
			loginUser.setClientKey(EncryptUtil.getPublicKeyFromByteArray(mPushMessageModel.getBody()));
			this.sendResponseEntryptment(loginUser, mPushMessageModel, context);
		} else {
			byte[] body = mPushMessageModel.getBody();
			byte[] decode = EncryptUtil.decode(loginUser.getClientKey(), loginUser.getPrivateKey(), body);
			mPushMessageModel.setBody(decode);
			mPushMessageModel.setBodyLenth((short) decode.length);

			boolean valid = this.mPushMessageServer.getServerConfiguration().getLoginUserAuthenticator()
					.isValid(loginUser);

			ServerConfiguration serverConfiguration = this.mPushMessageServer.getServerConfiguration();
			if (null == loginUser.getClientKey() ) {
				logger.debug("消息丢弃，应先交换加密密钥：" + mPushMessageModel);
				this.sendRetryEncryptment(loginUser, context);
			} else if (valid == false && mPushMessageModel.getMessageType() != MPushMessageType.AUTH_LOGIN_REQUEST) {
				logger.debug("消息丢弃，应先登录用户认证身份：" + mPushMessageModel);
				this.sendRetryAuthentication(mPushMessageModel, context);
			} else if (MPushMessageType.AUTH_LOGIN_REQUEST == mPushMessageModel.getMessageType()) {
				this.handleLoginRequest(mPushMessageModel, context);
			} else if (MPushMessageType.STATE_HEARTBEAT_RESPONSE == mPushMessageModel.getMessageType()) {
				loginUser.setLastHeartBeat(new Date());
			} else if (MPushMessageType.MESSAGE_DATABASE_CLIENT_RECV == mPushMessageModel.getMessageType()) {
				NotificationMessageService notificationMessageService = serverConfiguration
						.getNotificationMessageService();
				NotificationMessage notificationMessage = notificationMessageService
						.get(new String(mPushMessageModel.getBody()));
				if (notificationMessage != null) {
					notificationMessage.setReceiveTime(new Date());
					notificationMessageService.update(notificationMessage);
				}
			} else if (MPushMessageType.MESSAGE_DATABASE_CLIENT_READ == mPushMessageModel.getMessageType()) {
				NotificationMessageService notificationMessageService = serverConfiguration
						.getNotificationMessageService();
				NotificationMessage notificationMessage = notificationMessageService
						.get(new String(mPushMessageModel.getBody()));
				if (notificationMessage != null) {
					notificationMessage.setReadTime(new Date());
					notificationMessageService.update(notificationMessage);
				}
			} else {
				if ("hehe".equals(new String((byte[]) mPushMessageModel.getBody()))) {
					this.mPushMessageServer.sendMessage(loginUser, "haha");
				}
			}
		}

	}

	private void sendResponseEntryptment(LoginUser loginUser, MPushMessageModel requestMPushMessageModel,
			IoSession context) throws MPushMessageException, IOException, Exception {
		this.mPushMessageServer.sendMessage(loginUser, ServerMPushMessageHelper
				.responseEntrycptment(this.mPushMessageServer.getServerConfiguration(), loginUser));
	}

	private void sendRetryEncryptment(LoginUser loginUser, IoSession context)
			throws MPushMessageException, IOException, Exception {
		this.mPushMessageServer.sendMessage(loginUser, ServerMPushMessageHelper
				.retryEntrycptment(this.mPushMessageServer.getServerConfiguration(), loginUser));

	}

	private void handleLoginRequest(MPushMessageModel mPushMessageModel, IoSession context)
			throws MPushMessageException, IOException, Exception {
		logger.trace("trying to login...");
		byte[] body = mPushMessageModel.getBody();
		ServerAuthenticatedResult loginResult = this.mPushMessageServer.getServerConfiguration()
				.getAuthenticatorServerManger().login(body);
		LoginUser loginUser = this.mPushMessageServer.getServerConfiguration().getLoginUserManager()
				.getLoginUser(context);
		if (loginResult != null) {
			loginUser.setAuthentication(loginResult.toByteArray());
			loginUser.setUserId(loginResult.getUserId());
			// 登录成功
			this.sendLoginSuccessfully(loginUser, loginResult, mPushMessageModel);
			loginUser.setLastHeartBeat(new Date());
		} else {
			// 登录失败
			this.sendLoginUnsuccessfully(loginUser, mPushMessageModel);
		}
	}

	private void sendLoginUnsuccessfully(LoginUser loginUser, MPushMessageModel requestMPushMessageModel)
			throws MPushMessageException, IOException, Exception {

		logger.trace("sendLoginUnsuccessfully...");
		MPushMessageModel mPushMessageModel = new MPushMessageModel();
		mPushMessageModel.setMessageType(MPushMessageType.AUTH_LOGIN_RESPONSE_NO);
		mPushMessageModel.setRequestSequence(
				this.mPushMessageServer.getServerConfiguration().getRequestSequence().getAndIncrement());
		mPushMessageModel.setResponseSequence(requestMPushMessageModel.getRequestSequence());
		this.mPushMessageServer.sendMessage(loginUser, mPushMessageModel);
	}

	private void sendLoginSuccessfully(LoginUser loginUser, AuthenticatedResult loginResult,
			MPushMessageModel requestMPushMessageModel) throws MPushMessageException, IOException, Exception {
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
			throws MPushMessageException, IOException, Exception {
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
