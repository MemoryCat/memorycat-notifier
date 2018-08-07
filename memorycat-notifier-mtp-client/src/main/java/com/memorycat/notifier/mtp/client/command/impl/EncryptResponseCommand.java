package com.memorycat.notifier.mtp.client.command.impl;

import java.io.IOException;

import com.memorycat.notifier.mtp.client.ClientContext;
import com.memorycat.notifier.mtp.client.command.Command;
import com.memorycat.notifier.mtp.client.impl.MtpEntityHelper;
import com.memorycat.notifier.mtp.client.listener.FireListenerHelper;
import com.memorycat.notifier.mtp.core.entity.MtpEntity;
import com.memorycat.notifier.mtp.core.entity.auth.EncryptionDto;
import com.memorycat.notifier.mtp.core.exception.MtpEntityException;
import com.memorycat.notifier.mtp.core.util.Base64Util;
import com.memorycat.notifier.mtp.core.util.JsonUtil;
import com.memorycat.notifier.mtp.core.util.KeyUtil;

public class EncryptResponseCommand implements Command {

	@Override
	public void execute(ClientContext clientContext, MtpEntity requestMtpEntity)
			throws MtpEntityException, IOException, Exception {
		
		EncryptionDto encryptionDto = JsonUtil.fromByteArray(Base64Util.decode(requestMtpEntity.getBody()));
		clientContext.getUser().getUserDataEncryption()
				.setServerKey(KeyUtil.getPublicKeyFromEncryptionDto(encryptionDto));

		FireListenerHelper.notifyUserListener_afterEncrypt(clientContext, requestMtpEntity);
		FireListenerHelper.notifyUserListener_beforeLogin(clientContext, requestMtpEntity);
		// 开始登录
		clientContext.getMtpClient()
				.sendMessage(MtpEntityHelper.loginRequest(clientContext.getUser().getAuthenticator().auth()));

	}

}
