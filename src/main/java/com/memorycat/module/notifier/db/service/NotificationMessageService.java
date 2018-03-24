package com.memorycat.module.notifier.db.service;

import java.util.List;

import com.memorycat.module.notifier.db.model.NotificationMessage;
import com.memorycat.module.notifier.exception.NotifierException;

public interface NotificationMessageService {

	List<NotificationMessage> getAllUnsentMessages() throws NotifierException;

	List<NotificationMessage> getAllUnreadMessages() throws NotifierException;

	List<NotificationMessage> getAll() throws NotifierException;

	NotificationMessage get(String id) throws NotifierException;

	/**
	 * 
	 * @param notificationMessage
	 * @return 插入记录的主键
	 * @throws NotifierException
	 */
	String add(NotificationMessage notificationMessage) throws NotifierException;

	void update(NotificationMessage notificationMessage) throws NotifierException;

	void delete(String id) throws NotifierException;

}
