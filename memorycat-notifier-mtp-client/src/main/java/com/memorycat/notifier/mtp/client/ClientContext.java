package com.memorycat.notifier.mtp.client;

import java.util.List;

import com.memorycat.notifier.mtp.client.auth.User;
import com.memorycat.notifier.mtp.client.command.CommandFactory;
import com.memorycat.notifier.mtp.client.listener.ClientListener;

public interface ClientContext {

	MtpClient getMtpClient();

	void setMtpClient(MtpClient mtpClient);

	ConnectionConfig getConnectionConfig();

	User getUser();

	CommandFactory getCommandFactory();

	List<ClientListener> getListeners();

}
