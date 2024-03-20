package com.cloud.vijay.health_check.service;

import com.cloud.vijay.health_check.dao.HealthCheckDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;

@Service
public class HealthCheckService {

    @Autowired
    private HealthCheckDAO healthCheckDAO;
    private static final Logger LOGGER = LogManager.getLogger(HealthCheckService.class);

    public Boolean isDBConnected() {
        try {
            LOGGER.debug("Checking the DB Connection @HealthCheckService.isDBConnected()");
            Connection connection = healthCheckDAO.getConenction();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
