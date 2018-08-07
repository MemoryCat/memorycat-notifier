package com.memorycat.notifier.mtp.client.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.memorycat.notifier.mtp.client.ClientContext;
import com.memorycat.notifier.mtp.client.ConnectionConfig;
import com.memorycat.notifier.mtp.client.MtpClient;
import com.memorycat.notifier.mtp.client.auth.User;
import com.memorycat.notifier.mtp.client.command.CommandFactory;
import com.memorycat.notifier.mtp.client.command.CommandFactoryBuilder;
import com.memorycat.notifier.mtp.client.listener.ClientListener;

public class DefaultClientContext implements ClientContext {

	private MtpClient mtpClient;
	private ConnectionConfig connectionConfig;
	private final int port;
	private final String hostAddress;
	protected User user;
	protected CommandFactory commandFactory;
	protected final List<ClientListener> listeners = new CopyOnWriteArrayList<>();

	public DefaultClientContext() {
		this("localhost", 12345);

	}

	public DefaultClientContext(String hostAddress, int port) {
		this.hostAddress = hostAddress;
		this.port = port;
		this.connectionConfig = new DefaultConnectionConfig(this.hostAddress, this.port);
		this.user = new User();

		this.commandFactory = CommandFactoryBuilder.createDefault().build();

	}

	@Override
	public MtpClient getMtpClient() {
		return this.mtpClient;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void setMtpClient(MtpClient mtpClient) {
		this.mtpClient = mtpClient;
	}

	@Override
	public ConnectionConfig getConnectionConfig() {
		return this.connectionConfig;
	}

	public int getPort() {
		return port;
	}

	public String getHostAddress() {
		return hostAddress;
	}

	public void setConnectionConfig(ConnectionConfig connectionConfig) {
		this.connectionConfig = connectionConfig;
	}

	@Override
	public User getUser() {
		return this.user;
	}

	@Override
	public CommandFactory getCommandFactory() {
		return this.commandFactory;
	}

	public void setCommandFactory(CommandFactory commandFactory) {
		this.commandFactory = commandFactory;
	}

	@Override
	public List<ClientListener> getListeners() {
		return this.listeners;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commandFactory == null) ? 0 : commandFactory.hashCode());
		result = prime * result + ((connectionConfig == null) ? 0 : connectionConfig.hashCode());
		result = prime * result + ((hostAddress == null) ? 0 : hostAddress.hashCode());
		result = prime * result + ((listeners == null) ? 0 : listeners.hashCode());
		result = prime * result + ((mtpClient == null) ? 0 : mtpClient.hashCode());
		result = prime * result + port;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultClientContext other = (DefaultClientContext) obj;
		if (commandFactory == null) {
			if (other.commandFactory != null)
				return false;
		} else if (!commandFactory.equals(other.commandFactory))
			return false;
		if (connectionConfig == null) {
			if (other.connectionConfig != null)
				return false;
		} else if (!connectionConfig.equals(other.connectionConfig))
			return false;
		if (hostAddress == null) {
			if (other.hostAddress != null)
				return false;
		} else if (!hostAddress.equals(other.hostAddress))
			return false;
		if (listeners == null) {
			if (other.listeners != null)
				return false;
		} else if (!listeners.equals(other.listeners))
			return false;
		if (mtpClient == null) {
			if (other.mtpClient != null)
				return false;
		} else if (!mtpClient.equals(other.mtpClient))
			return false;
		if (port != other.port)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
