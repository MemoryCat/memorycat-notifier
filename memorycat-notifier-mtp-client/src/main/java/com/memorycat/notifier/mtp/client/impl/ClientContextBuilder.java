package com.memorycat.notifier.mtp.client.impl;

public class ClientContextBuilder {

	public static DefaultClientContext buildDefaultClientContext() {
		return new DefaultClientContext();
	}

	public static DefaultClientContext buildDefaultClientContext(String hostAddress, int port) {
		return new DefaultClientContext(hostAddress, port);
	}
}
