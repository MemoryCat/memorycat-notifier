package com.memorycat.module.notifier.mpush.server.auth;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.module.notifier.mpush.exception.auth.AuthenticationException;
import com.memorycat.module.notifier.mpush.exception.auth.PasswordErrorException;
import com.memorycat.module.notifier.mpush.exception.auth.UserNotFoundException;

/**
 * 服务端认证管理器，可以注册多个认证管理器，只要第1个认证器认证通过就算登录成功。 按顺序从先往后认证。
 * 
 * @author xie
 *
 */
public class AuthenticatorServerManger {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticatorServerManger.class);
	private final List<UserAuthenticator> serverAuthenticators = new LinkedList<UserAuthenticator>();

	public boolean contains(UserAuthenticator o) {
		return serverAuthenticators.contains(o);
	}

	public boolean register(UserAuthenticator e) {
		return serverAuthenticators.add(e);
	}

	public boolean unregister(UserAuthenticator o) {
		return serverAuthenticators.remove(o);
	}

	public void clear() {
		serverAuthenticators.clear();
	}

	public void add(int index, UserAuthenticator element) {
		serverAuthenticators.add(index, element);
	}

	public ServerAuthenticatedResult login(Object info) {
		ServerAuthenticatedResult loginResult = null;
		for (UserAuthenticator userAuthenticator : serverAuthenticators) {
			try {
				loginResult = userAuthenticator.login(info);
				break;
			} catch (UserNotFoundException e) {
				logger.info(e.getLocalizedMessage(), e);
			} catch (PasswordErrorException e) {
				logger.info(e.getLocalizedMessage(), e);
			} catch (AuthenticationException e) {
				logger.info(e.getLocalizedMessage(), e);
			} catch (Exception e) {
				logger.info(e.getLocalizedMessage(), e);
			}
		}
		return loginResult;
	}

	public static void main(String[] args) {
		AuthenticatorServerManger authenticatorServerManger = new AuthenticatorServerManger();
		// authenticatorServerManger.register(new UserAndPasswordAuthenticator());

		Object login = authenticatorServerManger.login("123");
		System.out.println(login);
	}
}
