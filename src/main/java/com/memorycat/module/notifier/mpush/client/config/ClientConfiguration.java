package com.memorycat.module.notifier.mpush.client.config;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.mina.core.session.IoSession;

import com.memorycat.module.notifier.mpush.client.MPushMessageClient;
import com.memorycat.module.notifier.mpush.client.listener.MPushClientListener;
import com.memorycat.module.notifier.mpush.client.model.ClientUser;

public class ClientConfiguration {
	private MPushMessageClient mPushMessageClient;
	private ClientUser clientUser = new ClientUser();
	private IoSession ioSession;
	private final AtomicLong requestSequence = new AtomicLong(1L);
	private final AtomicLong responseSequence = new AtomicLong(1L);
	private final List<MPushClientListener> listeners = new LinkedList<MPushClientListener>();
	private final String serverHost;
	private final int port;
	private final boolean useUdp;

	public ClientConfiguration(String serverHost, int port, boolean useUdp) {
		super();
		this.serverHost = serverHost;
		this.port = port;
		this.useUdp = useUdp;
	}

	public int getPort() {
		return port;
	}

	public String getServerHost() {
		return serverHost;
	}

	public MPushMessageClient getmPushMessageClient() {
		return mPushMessageClient;
	}

	public void setmPushMessageClient(MPushMessageClient mPushMessageClient) {
		this.mPushMessageClient = mPushMessageClient;
	}

	public IoSession getIoSession() {
		return ioSession;
	}

	public void setIoSession(IoSession ioSession) {
		this.ioSession = ioSession;
	}

	public AtomicLong getRequestSequence() {
		return requestSequence;
	}

	public AtomicLong getResponseSequence() {
		return responseSequence;
	}

	public List<MPushClientListener> getListeners() {
		return listeners;
	}

	public ClientUser getClientUser() {
		return clientUser;
	}

	public void setClientUser(ClientUser clientUser) {
		this.clientUser = clientUser;
	}

	public boolean isUseUdp() {
		return useUdp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientUser == null) ? 0 : clientUser.hashCode());
		result = prime * result + ((ioSession == null) ? 0 : ioSession.hashCode());
		result = prime * result + ((listeners == null) ? 0 : listeners.hashCode());
		result = prime * result + ((mPushMessageClient == null) ? 0 : mPushMessageClient.hashCode());
		result = prime * result + port;
		result = prime * result + ((requestSequence == null) ? 0 : requestSequence.hashCode());
		result = prime * result + ((responseSequence == null) ? 0 : responseSequence.hashCode());
		result = prime * result + ((serverHost == null) ? 0 : serverHost.hashCode());
		result = prime * result + (useUdp ? 1231 : 1237);
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
		ClientConfiguration other = (ClientConfiguration) obj;
		if (clientUser == null) {
			if (other.clientUser != null)
				return false;
		} else if (!clientUser.equals(other.clientUser))
			return false;
		if (ioSession == null) {
			if (other.ioSession != null)
				return false;
		} else if (!ioSession.equals(other.ioSession))
			return false;
		if (listeners == null) {
			if (other.listeners != null)
				return false;
		} else if (!listeners.equals(other.listeners))
			return false;
		if (mPushMessageClient == null) {
			if (other.mPushMessageClient != null)
				return false;
		} else if (!mPushMessageClient.equals(other.mPushMessageClient))
			return false;
		if (port != other.port)
			return false;
		if (requestSequence == null) {
			if (other.requestSequence != null)
				return false;
		} else if (!requestSequence.equals(other.requestSequence))
			return false;
		if (responseSequence == null) {
			if (other.responseSequence != null)
				return false;
		} else if (!responseSequence.equals(other.responseSequence))
			return false;
		if (serverHost == null) {
			if (other.serverHost != null)
				return false;
		} else if (!serverHost.equals(other.serverHost))
			return false;
		if (useUdp != other.useUdp)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClientConfiguration [mPushMessageClient=" + mPushMessageClient + ", clientUser=" + clientUser
				+ ", ioSession=" + ioSession + ", requestSequence=" + requestSequence + ", responseSequence="
				+ responseSequence + ", listeners=" + listeners + ", serverHost=" + serverHost + ", port=" + port
				+ ", useUdp=" + useUdp + "]";
	}

}
