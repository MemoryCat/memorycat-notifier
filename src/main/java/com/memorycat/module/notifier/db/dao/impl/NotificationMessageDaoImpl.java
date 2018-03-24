package com.memorycat.module.notifier.db.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memorycat.module.notifier.db.dao.NotificationMessageDao;
import com.memorycat.module.notifier.db.dao.NotificationMessageListResultSetHandler;
import com.memorycat.module.notifier.db.dao.NotificationMessageResultSetHandler;
import com.memorycat.module.notifier.db.model.NotificationMessage;

public class NotificationMessageDaoImpl implements NotificationMessageDao {

	private static final Logger logger = LoggerFactory.getLogger(NotificationMessageDaoImpl.class);
	private final Connection connection;
	private final QueryRunner queryRunner;

	public NotificationMessageDaoImpl(Connection connection) {
		super();
		this.connection = connection;
		this.queryRunner = new QueryRunner();
	}

	@Override
	public List<NotificationMessage> getAllUnsentMessages() throws SQLException {
		String sql = "select * from t_notifier_notificationmessage where receive_time is null";
		return this.queryRunner.query(connection, sql, new NotificationMessageListResultSetHandler());

	}

	@Override
	public List<NotificationMessage> getAllUnreadMessages() throws SQLException {

		String sql = "select * from t_notifier_notificationmessage where read_time is null";
		return this.queryRunner.query(connection, sql, new NotificationMessageListResultSetHandler());
	}

	@Override
	public List<NotificationMessage> getAll() throws SQLException {
		String sql = "select * from t_notifier_notificationmessage ";
		return this.queryRunner.query(connection, sql, new NotificationMessageListResultSetHandler());
	}

	@Override
	public NotificationMessage get(String id) throws SQLException {
		String sql = "select * from t_notifier_notificationmessage where id = ?";
		return this.queryRunner.query(connection, sql, new NotificationMessageResultSetHandler(), new Object[] { id });
	}

	@Override
	public String add(NotificationMessage notificationMessage) throws SQLException {
		String sql = "INSERT INTO t_notifier_notificationmessage (id,message_type, criticality, user_id, content, create_time, sent_time, receive_time, read_time) VALUES (?,?,?,?,?,?,?,?,?)";
		// TODO 未能返回数据库里的id
		String insert = this.queryRunner.insert(this.connection, sql, new ScalarHandler<String>(),
				new Object[] { notificationMessage.getId(), notificationMessage.getMessageType().toString(),
						notificationMessage.getCriticality().toString(), notificationMessage.getUserId(),
						notificationMessage.getContent(), notificationMessage.getCreateTime(),
						notificationMessage.getSentTime(), notificationMessage.getReceiveTime(),
						notificationMessage.getReadTime() });
		logger.trace("insert id :" + insert);
		return notificationMessage.getId();
	}

	@Override
	public void update(NotificationMessage notificationMessage) throws SQLException {
		String sql = "UPDATE  t_notifier_notificationmessage SET   message_type=?, criticality=?, user_id=?, content=?, create_time=?, sent_time=?, receive_time=?, read_time=? WHERE id=?";
		int update = this.queryRunner.update(connection, sql,
				new Object[] { notificationMessage.getMessageType().toString(),
						notificationMessage.getCriticality().toString(), notificationMessage.getUserId(),
						notificationMessage.getContent(), notificationMessage.getCreateTime(),
						notificationMessage.getSentTime(), notificationMessage.getReceiveTime(),
						notificationMessage.getReadTime(), notificationMessage.getId() });
		logger.trace("update rows :" + update);
	}

	@Override
	public void delete(String id) throws SQLException {
		logger.trace("try to delete row of id :" + id);
		String sql = "delete from t_notifier_notificationmessage where id= ?";
		int update = this.queryRunner.update(this.connection, sql, id);
		logger.trace("delete rows :" + update);
	}

}
