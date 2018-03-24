package com.memorycat.module.notifier.mpush.server;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.module.notifier.mpush.server.config.ServerConfiguration;
import com.memorycat.module.notifier.mpush.server.event.LoginUserEvent;
import com.memorycat.module.notifier.mpush.server.event.LoginUserEvent.EventType;
import com.memorycat.module.notifier.mpush.server.listener.MPushServerListener;
import com.memorycat.module.notifier.mpush.server.model.LoginUser;

public class ServerHeartBeatRunnable extends TimerTask implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(ServerHeartBeatRunnable.class);
	// private final SimpleDateFormat yyyyMMddHHmmss = new
	// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private ServerConfiguration serverConfiguration;

	public ServerHeartBeatRunnable(ServerConfiguration serverConfiguration) {
		super();
		if (serverConfiguration == null) {
			throw new NullPointerException();
		}
		this.serverConfiguration = serverConfiguration;
	}

	@Override
	public void run() {
		logger.trace("ServerHeartBeatRunnable started");
		Set<LoginUser> loginUsers = this.serverConfiguration.getLoginUserManager().getLoginUsers();
		if (loginUsers != null && loginUsers.size() > 0) {
			for (LoginUser loginUser : loginUsers) {
				try {
					// 只有登录了的帐号才开始心跳
					if (loginUser.getAuthentication() != null) {

						Date lastHeartBeat = loginUser.getLastHeartBeat();
						if (lastHeartBeat.getTime() == 0L) {// 未进行心跳，这应该是首次心跳

						} else {
							int heartBeatSeconds = this.serverConfiguration.getHeartBeatSeconds();
							int heartBeatFailedCount = this.serverConfiguration.getHeartBeatFailedCount();
							long maxTime = lastHeartBeat.getTime()
									+ (((heartBeatFailedCount + 1) * (heartBeatSeconds * 1000L)));
							Date maxDate = new Date(maxTime);
							Date now = new Date();
							// logger.trace("#################" + yyyyMMddHHmmss.format(maxDate) + "/"
							// + yyyyMMddHHmmss.format(now));
							if (now.equals(maxDate) || now.after(maxDate)) {
								// 超过心跳响应失败时间，但还未有任何响应，应判断该用户已掉线
								logger.trace("超过心跳响应失败时间，但还未有任何响应，应判断该用户已掉线。" + loginUser);
								loginUsers.remove(loginUser);
								List<MPushServerListener> listeners = this.serverConfiguration.getListeners();
								if (listeners != null && listeners.size() > 0) {
									for (MPushServerListener mPushServerListener : listeners) {
										mPushServerListener.removeLoginUser(
												new LoginUserEvent(this.serverConfiguration.getmPushMessageServer(),
														loginUser, EventType.REMOVE_USER));
									}
								}
								// 请求客户端重新尝试交换密钥( 登录)
								this.serverConfiguration.getmPushMessageServer().sendMessage(loginUser,
										ServerMPushMessageHelper.retryEntrycptment(serverConfiguration, loginUser));
								continue;
							}
						}

						this.serverConfiguration.getmPushMessageServer().sendMessage(loginUser,
								ServerMPushMessageHelper.heartBeat(this.serverConfiguration));
					}
				} catch (Exception e) {
					logger.warn(e.getLocalizedMessage(), e);
				}
			}
		}

		logger.trace("ServerHeartBeatRunnable end");
	}
}
