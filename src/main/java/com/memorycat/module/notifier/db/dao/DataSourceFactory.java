package com.memorycat.module.notifier.db.dao;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class DataSourceFactory {

	private static DataSource defaultDataSource = initDefaultDataSource();

	private static DataSource initDefaultDataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setUrl(
				"jdbc:mysql://localhost:3306/memorycat_mysite2?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull");
		basicDataSource.setUsername("root");
		basicDataSource.setPassword("");
		basicDataSource.setMinIdle(2);
		return basicDataSource;
	}

	public static DataSource getDefaultDataSource() {
		return defaultDataSource;
	}

	public static void setDefaultDataSource(DataSource defaultDataSource) {
		DataSourceFactory.defaultDataSource = defaultDataSource;
	}

}
