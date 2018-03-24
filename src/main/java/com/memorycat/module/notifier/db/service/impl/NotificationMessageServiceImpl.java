package com.memorycat.module.notifier.db.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.memorycat.module.notifier.db.dao.ConnectionFactory;
import com.memorycat.module.notifier.db.dao.IdGenerator;
import com.memorycat.module.notifier.db.dao.NotificationMessageDao;
import com.memorycat.module.notifier.db.dao.impl.NotificationMessageDaoImpl;
import com.memorycat.module.notifier.db.model.NotificationMessage;
import com.memorycat.module.notifier.db.service.NotificationMessageService;
import com.memorycat.module.notifier.exception.NotifierException;

public class NotificationMessageServiceImpl implements NotificationMessageService {

	@Override
	public List<NotificationMessage> getAllUnsentMessages() throws NotifierException {
		Connection connection = null;
		try {
			connection = ConnectionFactory.getDefaultConnection();
			NotificationMessageDao notificationMessageDao = new NotificationMessageDaoImpl(connection);
			return notificationMessageDao.getAllUnsentMessages();
		} catch (SQLException e) {
			throw new NotifierException(e);
		} finally {
			ConnectionFactory.closeConnection(connection);
		}
	}

	@Override
	public List<NotificationMessage> getAllUnreadMessages() throws NotifierException {
		Connection connection = null;
		try {
			connection = ConnectionFactory.getDefaultConnection();
			NotificationMessageDao notificationMessageDao = new NotificationMessageDaoImpl(connection);
			return notificationMessageDao.getAllUnreadMessages();
		} catch (SQLException e) {
			throw new NotifierException(e);
		} finally {
			ConnectionFactory.closeConnection(connection);
		}
	}

	@Override
	public List<NotificationMessage> getAll() throws NotifierException {
		Connection connection = null;
		try {
			connection = ConnectionFactory.getDefaultConnection();
			NotificationMessageDao notificationMessageDao = new NotificationMessageDaoImpl(connection);
			return notificationMessageDao.getAll();
		} catch (SQLException e) {
			throw new NotifierException(e);
		} finally {
			ConnectionFactory.closeConnection(connection);
		}
	}

	@Override
	public NotificationMessage get(String id) throws NotifierException {
		Connection connection = null;
		try {
			connection = ConnectionFactory.getDefaultConnection();
			NotificationMessageDao notificationMessageDao = new NotificationMessageDaoImpl(connection);
			return notificationMessageDao.get(id);
		} catch (SQLException e) {
			throw new NotifierException(e);
		}
	}

	@Override
	public String add(NotificationMessage notificationMessage) throws NotifierException {
		if (notificationMessage == null) {
			throw new NullPointerException();
		}
		Connection connection = null;
		try {
			connection = ConnectionFactory.getDefaultConnection();
			NotificationMessageDao notificationMessageDao = new NotificationMessageDaoImpl(connection);
			if (StringUtils.isBlank(notificationMessage.getId())) {
				notificationMessage.setId(IdGenerator.generateUuid());
			}
			return notificationMessageDao.add(notificationMessage);
		} catch (SQLException e) {
			throw new NotifierException(e);
		} finally {
			ConnectionFactory.closeConnection(connection);
		}
	}

	@Override
	public void update(NotificationMessage notificationMessage) throws NotifierException {
		if (notificationMessage == null) {
			throw new NullPointerException();
		}
		Connection connection = null;
		try {
			connection = ConnectionFactory.getDefaultConnection();
			NotificationMessageDao notificationMessageDao = new NotificationMessageDaoImpl(connection);
			notificationMessageDao.update(notificationMessage);
		} catch (SQLException e) {
			throw new NotifierException(e);
		} finally {
			ConnectionFactory.closeConnection(connection);
		}
	}

	@Override
	public void delete(String id) throws NotifierException {
		if (StringUtils.isBlank(id)) {
			throw new IllegalArgumentException("id should not be null or emtry");
		}
		Connection connection = null;
		try {
			connection = ConnectionFactory.getDefaultConnection();
			NotificationMessageDao notificationMessageDao = new NotificationMessageDaoImpl(connection);
			notificationMessageDao.delete(id);
		} catch (SQLException e) {
			throw new NotifierException(e);
		} finally {
			ConnectionFactory.closeConnection(connection);
		}
	}

}
