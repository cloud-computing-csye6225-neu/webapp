package com.cloud.vijay.health_check.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;

@Repository
public class HealthCheckDAO {

    @Autowired
    private DataSource dataSource;

    public Connection getConenction() throws Exception {

        Connection connection = dataSource.getConnection();
        return connection;

    }
}
