package com.memorycat.notifier.mtp.client;

import org.apache.mina.core.service.IoConnector;

public interface ConnectionConfig {

	/**
	 * 是否使用TCP，是：tcp；否：udp
	 * 
	 * @return
	 */
	boolean usingTcp();

	/**
	 * 链接端口
	 * 
	 * @return
	 */
	int getPort();

	/**
	 * 链接地址
	 * 
	 * @return
	 */
	String getHostAddress();

	/**
	 * 是否为阻塞模式
	 * 
	 * @return
	 */
	boolean blocking();

	IoConnector getIoConnector();
}
