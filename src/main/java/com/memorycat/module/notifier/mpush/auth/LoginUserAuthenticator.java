package com.memorycat.module.notifier.mpush.auth;

import com.memorycat.module.notifier.mpush.server.model.LoginUser;

/**
 * 验证登录用户的验证信息：比如说验证登录超时之类的。再比如他已经改密码了原密码还能不能用之类的
 * 
 * @author xie
 *
 */
public class LoginUserAuthenticator {

	public boolean isValid(LoginUser loginUser) {
		if (loginUser == null) {
			throw new NullPointerException();
		}
		// TODO 验证登录算法未实现
		if (null != loginUser.getAuthentication()) {
			return true;
		}

		return false;
	}
}
