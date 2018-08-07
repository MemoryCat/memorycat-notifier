package com.memorycat.notifier.mtp.client.event;

import com.memorycat.notifier.mtp.client.MtpClient;
import com.memorycat.notifier.mtp.core.entity.MtpEntity;

public class HeartBeatEvent extends MtpEntityMessageEvent {

	private static final long serialVersionUID = -5820112757522924771L;

	public HeartBeatEvent(MtpClient mtpClient, MtpEntity mtpEntity) {
		super(mtpClient, mtpEntity);
	}
}
