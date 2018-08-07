package com.memorycat.notifier.mtp.client;

import com.memorycat.notifier.mtp.client.impl.DefaultClientContext;
import com.memorycat.notifier.mtp.client.impl.DefaultMtpClient;
import com.memorycat.notifier.mtp.core.exception.MemoryCatNotifierException;

public class MtpClientFactory {

	public static MtpClient createMtpClient(ClientContext clientContext) throws MemoryCatNotifierException {
		return new DefaultMtpClient(clientContext);
	}

	public static DefaultMtpClient createDefaultMtpClient(String hostAddress, int port)
			throws MemoryCatNotifierException {
		return new DefaultMtpClient(new DefaultClientContext(hostAddress, port));
	}
}
