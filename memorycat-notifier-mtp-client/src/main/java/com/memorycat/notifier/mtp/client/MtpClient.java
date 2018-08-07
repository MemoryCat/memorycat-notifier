package com.memorycat.notifier.mtp.client;

import java.io.Closeable;
import java.io.IOException;

import com.memorycat.notifier.mtp.core.entity.MtpEntity;
import com.memorycat.notifier.mtp.core.exception.MtpEntityException;

public interface MtpClient extends Closeable {

	ClientContext getClientContext();

	MtpEntity sendMessage(Object message) throws MtpEntityException, IOException, Exception;

	/**
	 * 开始交换密钥。
	 * 
	 * 正常通讯前需要交换密钥和登录认证。交换密钥后会自动再次登录。
	 * 
	 * 如果是用户登录失败要重试登录，修改认证方式后，再次调用该方法重新执行步骤就行了。
	 * 
	 * @return
	 * @throws MtpEntityException
	 * @throws IOException
	 * @throws Exception
	 */
	MtpEntity startEncrypt() throws MtpEntityException, IOException, Exception;

}
