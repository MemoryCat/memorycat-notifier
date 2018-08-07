package com.memorycat.notifier.mtp.client.event;

import com.memorycat.notifier.mtp.client.MtpClient;
import com.memorycat.notifier.mtp.client.auth.User;
import com.memorycat.notifier.mtp.core.entity.MtpEntity;

public class UserEvent extends MtpEntityMessageEvent {

	private static final long serialVersionUID = 2842959258652856181L;

	protected final User user;

	public UserEvent(MtpClient mtpClient, MtpEntity mtpEntity, User user) {
		super(mtpClient, mtpEntity);
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		UserEvent other = (UserEvent) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserEvent [user=" + user + ", mtpEntity=" + mtpEntity + ", mtpClient=" + mtpClient + "]";
	}

}
