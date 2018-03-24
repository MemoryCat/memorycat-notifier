package com.memorycat.module.notifier.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import com.memorycat.module.notifier.db.model.Criticality;
import com.memorycat.module.notifier.db.model.MessageType;
import com.memorycat.module.notifier.db.model.NotificationMessage;

public class NotificationMessageResultSetUtil {

	public static NotificationMessage currentRestultSetToNotificatoinMessage(ResultSet rs) throws SQLException {
		if (rs == null) {
			throw new NullPointerException();
		}
		NotificationMessage notificationMessage = new NotificationMessage();
		notificationMessage.setId(rs.getString("id"));
		String messageType = rs.getString("message_type");
		notificationMessage
				.setMessageType(StringUtils.isNotBlank(messageType) ? MessageType.valueOf(messageType.toUpperCase())
						: MessageType.TEXT);
		String criticality = rs.getString("criticality");
		notificationMessage
				.setCriticality(StringUtils.isNoneBlank(criticality) ? Criticality.valueOf(criticality.toUpperCase())
						: Criticality.COMMON);
		notificationMessage.setUserId(rs.getString("user_id"));
		notificationMessage.setContent(rs.getString("content"));
		notificationMessage.setCreateTime(rs.getTimestamp("create_time"));
		notificationMessage.setSentTime(rs.getTimestamp("create_time"));
		notificationMessage.setReceiveTime(rs.getTimestamp("receive_time"));
		notificationMessage.setReadTime(rs.getTimestamp("read_time"));
		return notificationMessage;

	}
}
