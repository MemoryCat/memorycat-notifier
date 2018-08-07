package com.memorycat.notifier.mtp.core.entity.message;

import java.io.Serializable;
import java.util.Date;

public class NotificationMessage implements Serializable {

	private static final long serialVersionUID = -3081493209185392371L;

	/**
	 * 消息id
	 * 
	 * 建议使用uuid
	 */
	private String id ;//= UuidUtil.generateUUID();
	/**
	 * 消息类型
	 */
	private MessageType messageType = MessageType.TEXT;
	/**
	 * 消息重要程度
	 */
	private Criticality criticality = Criticality.COMMON;
	/**
	 * 接收消息的用户
	 */
	private String userId;
	/**
	 * 消息内容
	 */
	private String content;
	/**
	 * 消息创建时间
	 */
	private Date createTime = new Date();
	/**
	 * 服务端发送时间
	 */
	private Date sentTime;
	/**
	 * 客户端确认接收时间
	 */
	private Date receiveTime;
	/**
	 * 客户端确定阅读消息时间
	 */
	private Date readTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public Criticality getCriticality() {
		return criticality;
	}

	public void setCriticality(Criticality criticality) {
		this.criticality = criticality;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getSentTime() {
		return sentTime;
	}

	public void setSentTime(Date sentTime) {
		this.sentTime = sentTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((criticality == null) ? 0 : criticality.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((messageType == null) ? 0 : messageType.hashCode());
		result = prime * result + ((readTime == null) ? 0 : readTime.hashCode());
		result = prime * result + ((receiveTime == null) ? 0 : receiveTime.hashCode());
		result = prime * result + ((sentTime == null) ? 0 : sentTime.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotificationMessage other = (NotificationMessage) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (criticality != other.criticality)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (messageType != other.messageType)
			return false;
		if (readTime == null) {
			if (other.readTime != null)
				return false;
		} else if (!readTime.equals(other.readTime))
			return false;
		if (receiveTime == null) {
			if (other.receiveTime != null)
				return false;
		} else if (!receiveTime.equals(other.receiveTime))
			return false;
		if (sentTime == null) {
			if (other.sentTime != null)
				return false;
		} else if (!sentTime.equals(other.sentTime))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NotificationMessage [id=" + id + ", messageType=" + messageType + ", criticality=" + criticality
				+ ", userId=" + userId + ", content=" + content + ", createTime=" + createTime + ", sentTime="
				+ sentTime + ", receiveTime=" + receiveTime + ", readTime=" + readTime + "]";
	}

}
