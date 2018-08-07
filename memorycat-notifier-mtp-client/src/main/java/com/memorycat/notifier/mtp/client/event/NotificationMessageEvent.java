package com.memorycat.notifier.mtp.client.event;

import com.memorycat.notifier.mtp.client.MtpClient;
import com.memorycat.notifier.mtp.core.entity.MtpEntity;
import com.memorycat.notifier.mtp.core.entity.message.NotificationMessage;

public class NotificationMessageEvent extends MtpEntityMessageEvent {

	private static final long serialVersionUID = 5126141068047912500L;
	protected final NotificationMessage notificationMessage;
	protected final String id;

	public NotificationMessageEvent(MtpClient mtpClient, MtpEntity mtpEntity, NotificationMessage notificationMessage) {
		super(mtpClient, mtpEntity);
		this.notificationMessage = notificationMessage;
		this.id = null;
	}

	public NotificationMessageEvent(MtpClient mtpClient, MtpEntity mtpEntity, String id) {
		super(mtpClient, mtpEntity);
		this.notificationMessage = null;
		this.id = id;
	}

	public NotificationMessage getNotificationMessage() {
		return notificationMessage;
	}

	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((notificationMessage == null) ? 0 : notificationMessage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotificationMessageEvent other = (NotificationMessageEvent) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (notificationMessage == null) {
			if (other.notificationMessage != null)
				return false;
		} else if (!notificationMessage.equals(other.notificationMessage))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NotificationMessageEvent [notificationMessage=" + notificationMessage + ", id=" + id + ", mtpEntity="
				+ mtpEntity + ", mtpClient=" + mtpClient + "]";
	}

}
