package com.memorycat.module.notifier.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

import com.memorycat.module.notifier.db.model.NotificationMessage;

public class NotificationMessageResultSetHandler implements ResultSetHandler<NotificationMessage> {

	@Override
	public NotificationMessage handle(ResultSet rs) throws SQLException {
		if (rs != null) {
			while (rs.next()) {
				return NotificationMessageResultSetUtil.currentRestultSetToNotificatoinMessage(rs);
			}
		}
		return null;
	}

}
