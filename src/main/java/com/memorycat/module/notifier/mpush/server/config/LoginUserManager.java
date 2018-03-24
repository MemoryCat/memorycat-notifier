package com.memorycat.module.notifier.mpush.server.config;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.UnsupportedAddressTypeException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.mina.core.session.IoSession;

import com.memorycat.module.notifier.mpush.server.model.ConnectionAddress;
import com.memorycat.module.notifier.mpush.server.model.LoginUser;

public class LoginUserManager {

	private final Set<LoginUser> loginUsers = new CopyOnWriteArraySet<>();

	public boolean add(LoginUser loginUser) {
		return loginUsers.add(loginUser);
	}

	public boolean remove(LoginUser loginUser) {
		return loginUsers.remove(loginUser);
	}

	public int size() {
		return loginUsers.size();
	}

	public boolean isEmpty() {
		return loginUsers.isEmpty();
	}

	public boolean contains(LoginUser o) {
		return loginUsers.contains(o);
	}

	public Set<LoginUser> getLoginUsers() {
		return loginUsers;
	}

	/**
	 * 在内部维护的集合中查找LoginUser
	 * 
	 * @param connectionAddress
	 * @return 如果没找到则返回null
	 */
	public LoginUser findByConnectionAddress(ConnectionAddress connectionAddress) {
		for (LoginUser loginUser : loginUsers) {
			if (loginUser.getConnectionAddress().equals(connectionAddress)) {
				return loginUser;
			}
		}
		return null;
	}

	public LoginUser findByUserId(String userId) {
		if (userId != null) {
			for (LoginUser loginUser : loginUsers) {
				if (userId.equals(loginUser.getUserId())) {
					return loginUser;
				}
			}
		}
		return null;
	}

	/**
	 * 类似{@link #findByConnectionAddress(ConnectionAddress)},但是如果内部没的话会创建一个新的LoginUser
	 * 
	 * @param connectionAddress
	 * @return 始终会返回一个LoginUser（如果没找到的话，会自动创建一个新的被维护的LoginUser）
	 */
	public LoginUser getLoginUser(ConnectionAddress connectionAddress) {
		LoginUser findByConnectionAddress = this.findByConnectionAddress(connectionAddress);
		if (findByConnectionAddress != null) {
			return findByConnectionAddress;
		} else {
			LoginUser loginUser = new LoginUser();
			this.add(loginUser);
			loginUser.setConnectionAddress(connectionAddress);
			return loginUser;
		}
	}

	/**
	 * {@link #getLoginUser(ConnectionAddress)}
	 * 
	 * @param ioSession
	 * @return
	 */
	public LoginUser getLoginUser(IoSession ioSession) {
		SocketAddress remoteAddress = ioSession.getRemoteAddress();
		if (!(remoteAddress instanceof InetSocketAddress)) {
			throw new UnsupportedAddressTypeException();
		}
		InetSocketAddress inetSocketAddress = (InetSocketAddress) remoteAddress;
		LoginUser loginUser = this.getLoginUser(
				new ConnectionAddress(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress.getPort()));
		return loginUser;
	}
}
