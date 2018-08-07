package com.memorycat.notifier.mtp.client.auth.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.notifier.mtp.client.auth.Authenticator;
import com.memorycat.notifier.mtp.core.entity.auth.AccountPasswordEntity;
import com.memorycat.notifier.mtp.core.exception.AuthenticationException;
import com.memorycat.notifier.mtp.core.util.JsonUtil;

public class AccountPasswordAuthenticator implements Authenticator {

	private static final Logger logger = LoggerFactory.getLogger(AccountPasswordAuthenticator.class);
	private final AccountPasswordEntity accountPasswordEntity;

	public AccountPasswordAuthenticator(AccountPasswordEntity accountPasswordEntity) {
		super();
		if (accountPasswordEntity == null) {
			throw new NullPointerException();
		}
		this.accountPasswordEntity = accountPasswordEntity;
	}

	public AccountPasswordEntity getAccountPasswordEntity() {
		return accountPasswordEntity;
	}

	@Override
	public byte[] auth() throws AuthenticationException {
		try {
			return JsonUtil.toByteArray(accountPasswordEntity);
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage(), e);
			throw new AuthenticationException(e);
		}
	}

}
