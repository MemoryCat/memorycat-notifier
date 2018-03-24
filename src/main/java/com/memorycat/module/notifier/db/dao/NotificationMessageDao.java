package com.memorycat.module.notifier.db.dao;

import java.sql.SQLException;
import java.util.List;

import com.memorycat.module.notifier.db.model.NotificationMessage;

public interface NotificationMessageDao {

	List<NotificationMessage> getAllUnsentMessages() throws SQLException;

	List<NotificationMessage> getAllUnreadMessages() throws SQLException;

	List<NotificationMessage> getAll() throws SQLException;

	NotificationMessage get(String id) throws SQLException;

	String add(NotificationMessage notificationMessage) throws SQLException;

	void update(NotificationMessage notificationMessage) throws SQLException;

	void delete(String id) throws SQLException;

}
