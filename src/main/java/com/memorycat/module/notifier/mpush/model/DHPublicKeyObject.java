package com.memorycat.module.notifier.mpush.model;

import java.io.Serializable;

import javax.crypto.interfaces.DHPublicKey;

public class DHPublicKeyObject implements Serializable {

	private static final long serialVersionUID = 1175153411096825614L;
	private String y;
	private String p;
	private String g;

	public DHPublicKeyObject() {
		super();
	}

	public DHPublicKeyObject(String y, String p, String g) {
		this.y = y;
		this.p = p;
		this.g = g;
	}

	public DHPublicKeyObject(DHPublicKey dhPublicKey) {
		this.y = dhPublicKey.getY().toString();
		this.p = dhPublicKey.getParams().getP().toString();
		this.g = dhPublicKey.getParams().getG().toString();
	}

	public String getY() {
		return y;
	}

	public String getP() {
		return p;
	}

	public String getG() {
		return g;
	}

	public void setY(String y) {
		this.y = y;
	}

	public void setP(String p) {
		this.p = p;
	}

	public void setG(String g) {
		this.g = g;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((g == null) ? 0 : g.hashCode());
		result = prime * result + ((p == null) ? 0 : p.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
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
		DHPublicKeyObject other = (DHPublicKeyObject) obj;
		if (g == null) {
			if (other.g != null)
				return false;
		} else if (!g.equals(other.g))
			return false;
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DHPublicKeyObject [y=" + y + ", p=" + p + ", g=" + g + "]";
	}

}