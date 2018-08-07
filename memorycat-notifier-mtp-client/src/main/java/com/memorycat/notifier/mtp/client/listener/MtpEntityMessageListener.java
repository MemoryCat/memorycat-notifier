package com.memorycat.notifier.mtp.client.listener;

import com.memorycat.notifier.mtp.client.event.MtpEntityMessageEvent;

/**
 * MtpEntity消息的监听器，这个监听器监听的事件比较接近底层，基本上所有的消息都会流经该监听器，包括一些用户不应该处理的。因此不建议用户使用该监听器。用户应该使用具体消息的具体监听器（如：NotificationMessageListener）。
 * 
 * @author xie
 *
 */
public interface MtpEntityMessageListener {

	void send(MtpEntityMessageEvent event);

	void recv(MtpEntityMessageEvent event);

	void drop(MtpEntityMessageEvent event);
}
