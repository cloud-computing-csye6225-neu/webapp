package com.cloud.vijay.health_check.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import java.sql.Connection;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;

@Repository
public class HealthCheckDAO {
	
	@Autowired
	private DataSource dataSource;
	public Connection getConenction() throws Exception {

		Connection connection = dataSource.getConnection();
		return connection;

	}
}
