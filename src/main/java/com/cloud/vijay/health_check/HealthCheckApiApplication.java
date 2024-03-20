package com.cloud.vijay.health_check;

import com.cloud.vijay.health_check.filter.LoggingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HealthCheckApiApplication {

    private static final Logger LOGGER = LogManager.getLogger(HealthCheckApiApplication.class);
    public static void main(String[] args) {
        LOGGER.info("Debug message");
        SpringApplication.run(HealthCheckApiApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
        System.out.println("logging config");
        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();

        System.out.println("setting all filters");
        registrationBean.setFilter(new LoggingFilter());
        // Add URL patterns to apply this filter to
        registrationBean.addUrlPatterns("/*");
        System.out.println("logging config");

        return registrationBean;
    }
}
