package com.memorycat.notifier.mtp.client.event;

import com.memorycat.notifier.mtp.client.MtpClient;
import com.memorycat.notifier.mtp.core.entity.MtpEntity;

public class MtpEntityMessageEvent extends MtpClientEvent {

	private static final long serialVersionUID = 1638503566246549173L;

	protected final MtpEntity mtpEntity;

	public MtpEntityMessageEvent(MtpClient mtpClient, MtpEntity mtpEntity) {
		super(mtpClient);
		this.mtpEntity = mtpEntity;
	}

	public MtpEntity getMtpEntity() {
		return mtpEntity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((mtpEntity == null) ? 0 : mtpEntity.hashCode());
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
		MtpEntityMessageEvent other = (MtpEntityMessageEvent) obj;
		if (mtpEntity == null) {
			if (other.mtpEntity != null)
				return false;
		} else if (!mtpEntity.equals(other.mtpEntity))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MtpEntityMessageEvent [mtpEntity=" + mtpEntity + ", mtpClient=" + mtpClient + "]";
	}

}
