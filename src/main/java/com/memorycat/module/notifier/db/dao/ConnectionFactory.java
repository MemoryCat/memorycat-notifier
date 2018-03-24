package com.memorycat.module.notifier.db.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionFactory {

	private static final Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

	public static Connection getDefaultConnection() throws SQLException {
		DataSource dataSource = DataSourceFactory.getDefaultDataSource();
		Connection connection = dataSource.getConnection();
		logger.trace("getDefaultConnection():" + connection);
		return connection;
	}

	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.warn(e.getLocalizedMessage(), e);
			}
		}
	}
}
