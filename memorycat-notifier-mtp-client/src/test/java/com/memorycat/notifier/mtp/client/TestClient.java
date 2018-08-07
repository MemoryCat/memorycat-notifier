package com.memorycat.notifier.mtp.client;

import java.io.IOException;

import com.memorycat.notifier.mtp.client.auth.impl.AccountPasswordAuthenticator;
import com.memorycat.notifier.mtp.client.event.HeartBeatEvent;
import com.memorycat.notifier.mtp.client.event.MtpClientEvent;
import com.memorycat.notifier.mtp.client.event.NotificationMessageEvent;
import com.memorycat.notifier.mtp.client.event.UserEvent;
import com.memorycat.notifier.mtp.client.impl.ClientContextBuilder;
import com.memorycat.notifier.mtp.client.impl.DefaultClientContext;
import com.memorycat.notifier.mtp.client.listener.HeartBeatListener;
import com.memorycat.notifier.mtp.client.listener.MtpClientListener;
import com.memorycat.notifier.mtp.client.listener.NotificationMessageListener;
import com.memorycat.notifier.mtp.client.listener.UserListener;
import com.memorycat.notifier.mtp.core.entity.auth.AccountPasswordEntity;
import com.memorycat.notifier.mtp.core.exception.MtpEntityException;

public class TestClient {
	public static void main(String[] args) throws MtpEntityException, IOException, Exception {
		System.out.println("client start");

		DefaultClientContext defaultClientContext = ClientContextBuilder.buildDefaultClientContext("127.0.0.1", 12345);

		defaultClientContext.getUser()
				.setAuthenticator(new AccountPasswordAuthenticator(new AccountPasswordEntity("anonymous", "")));
		defaultClientContext.getListeners().add(new NotificationMessageListener() {

			@Override
			public void recv(NotificationMessageEvent event) {
				System.out.println("监听器：接收nm：" + event);
			}

			@Override
			public void read(NotificationMessageEvent event) {
				System.out.println("监听器：阅读nm：" + event);
			}
		});
		defaultClientContext.getListeners().add(new MtpClientListener() {

			@Override
			public void beforeConnect(MtpClientEvent event) {

				System.out.println("监听器：开始连接：" + event);
			}

			@Override
			public void beforeClose(MtpClientEvent event) {
				System.out.println("监听器：关闭连接：" + event);
			}

			@Override
			public void afterConnected(MtpClientEvent event) {
				System.out.println("监听器：已链接：" + event);
			}
		});

		defaultClientContext.getListeners().add(new UserListener() {

			@Override
			public void beforeLogin(UserEvent event) {
				System.out.println("监听器：开始登录：" + event);
			}

			@Override
			public void beforeEncrypt(UserEvent event) {
				System.out.println("监听器：开始加密：" + event);
			}

			@Override
			public void afterLogin(UserEvent event) {
				System.out.println("监听器：结束登录：" + event);
			}

			@Override
			public void afterEncrypt(UserEvent event) {
				System.out.println("监听器：结束加密：" + event);
			}
		});
		defaultClientContext.getListeners().add(new HeartBeatListener() {

			@Override
			public void heatBeatResponse(HeartBeatEvent event) {
				System.out.println("监听器：心跳响应：" + event);
			}

			@Override
			public void heartBeatRequest(HeartBeatEvent event) {
				System.out.println("监听器：心跳请求：" + event);
			}
		});
		MtpClient mtpClient = MtpClientFactory.createMtpClient(defaultClientContext);
		mtpClient.startEncrypt();
		System.out.println("client end");
	}
}
