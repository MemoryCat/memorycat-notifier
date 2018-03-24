package com.memorycat.module.notifier.mpush.server;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.module.notifier.db.model.NotificationMessage;
import com.memorycat.module.notifier.db.service.NotificationMessageService;
import com.memorycat.module.notifier.exception.NotifierException;
import com.memorycat.module.notifier.mpush.server.config.LoginUserManager;
import com.memorycat.module.notifier.mpush.server.config.ServerConfiguration;
import com.memorycat.module.notifier.mpush.server.model.LoginUser;
import com.memorycat.module.notifier.mpush.util.Constants;

public class ServerDatabaseMessageRunnable extends TimerTask implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(ServerDatabaseMessageRunnable.class);
	private final ServerConfiguration serverConfiguration;
	private final NotificationMessageService notificationMessageService;

	public ServerDatabaseMessageRunnable(ServerConfiguration serverConfiguration) {
		super();
		this.serverConfiguration = serverConfiguration;
		this.notificationMessageService = serverConfiguration.getNotificationMessageService();
		if (this.notificationMessageService == null) {
			throw new NullPointerException(Constants.EXCEPTION_SERVER_CONFIGURATION_NULLPOINT_NOTIFICATIONSERVICE);
		}
	}

	@Override
	public void run() {
//		logger.trace("ServerDatabaseMessageRunnable started");
		try {
			List<NotificationMessage> allUnsentMessages = this.notificationMessageService.getAllUnsentMessages();
			LoginUserManager loginUserManager = this.serverConfiguration.getLoginUserManager();
			if (allUnsentMessages != null && allUnsentMessages.size() > 0) {
				for (NotificationMessage notificationMessage : allUnsentMessages) {
					LoginUser loginUser = loginUserManager.findByUserId(notificationMessage.getUserId());
					if (loginUser != null) {
						MPushMessageServer mPushMessageServer = this.serverConfiguration.getmPushMessageServer();
						logger.debug("trying to send dbMessage to loginUser(" + loginUser.getUserId() + ")");
						mPushMessageServer.sendMessage(loginUser, ServerMPushMessageHelper
								.dbNotificationMessage(this.serverConfiguration, notificationMessage));
					}
				}
			}
		} catch (NotifierException e) {
			logger.warn(e.getLocalizedMessage(), e);
		} catch (IOException e) {
			logger.warn(e.getLocalizedMessage(), e);
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage(), e);
		}
//		logger.trace("ServerDatabaseMessageRunnable end");
	}

}
