package com.memorycat.notifier.mtp.client.event;

import java.util.EventObject;

import com.memorycat.notifier.mtp.client.MtpClient;

public class MtpClientEvent extends EventObject {

	private static final long serialVersionUID = -8152825989495764097L;
	protected final MtpClient mtpClient;

	public MtpClientEvent(MtpClient mtpClient) {
		super(mtpClient);
		this.mtpClient = mtpClient;
	}

	public MtpClient getMtpClient() {
		return mtpClient;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mtpClient == null) ? 0 : mtpClient.hashCode());
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
		MtpClientEvent other = (MtpClientEvent) obj;
		if (mtpClient == null) {
			if (other.mtpClient != null)
				return false;
		} else if (!mtpClient.equals(other.mtpClient))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MtpClientEvent [mtpClient=" + mtpClient + "]";
	}

}
