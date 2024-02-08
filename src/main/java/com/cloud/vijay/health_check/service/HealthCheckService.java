package com.cloud.vijay.health_check.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Connection;

import com.cloud.vijay.health_check.dao.HealthCheckDAO;

@Service
public class HealthCheckService {
	
	@Autowired
	private HealthCheckDAO healthCheckDAO;
	

	public Boolean isDBConnected() {
		try {
			Connection connection = healthCheckDAO.getConenction();
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
