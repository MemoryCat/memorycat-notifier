package com.memorycat.module.notifier.mpush.server.model;

import java.io.Serializable;

/**
 * 表示一个链接地址，由ip+端口可以唯一标识链接
 * 
 * @author xie
 *
 */
public class ConnectionAddress implements Serializable {

	private static final long serialVersionUID = -2678657222107360511L;

	private final String ip;
	private final int port;

	public ConnectionAddress(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + port;
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
		ConnectionAddress other = (ConnectionAddress) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (port != other.port)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ConnectionAddress [ip=" + ip + ", port=" + port + "]";
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

}
