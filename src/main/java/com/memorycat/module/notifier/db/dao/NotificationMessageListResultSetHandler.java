package com.memorycat.module.notifier.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

import com.memorycat.module.notifier.db.model.NotificationMessage;

public class NotificationMessageListResultSetHandler implements ResultSetHandler<List<NotificationMessage>> {

	@Override
	public List<NotificationMessage> handle(ResultSet rs) throws SQLException {
		List<NotificationMessage> returnValue = new LinkedList<NotificationMessage>();
		if (rs != null) {
			while (rs.next()) {
				returnValue.add(NotificationMessageResultSetUtil.currentRestultSetToNotificatoinMessage(rs));
			}
		}
		return returnValue;
	}

}
