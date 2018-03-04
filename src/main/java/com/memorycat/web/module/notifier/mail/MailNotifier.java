package com.memorycat.web.module.notifier.mail;

import com.memorycat.net.mail.commons.memorycat.MemoryCatQQSimpleMail;

public class MailNotifier {

	private static final String mailTo = "56022440@qq.com";

	public static void notifyMessage(String title, String content) throws Exception {
		new MemoryCatQQSimpleMail(mailTo, title, content).send();
	}
}
