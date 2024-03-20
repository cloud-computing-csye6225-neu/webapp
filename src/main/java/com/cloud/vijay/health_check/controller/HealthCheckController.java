package com.cloud.vijay.health_check.controller;

import com.cloud.vijay.health_check.HealthCheckApiApplication;
import com.cloud.vijay.health_check.service.HealthCheckService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
public class HealthCheckController {

    @Autowired
    private HealthCheckService healthCheckService;
    private static final Logger LOGGER = LogManager.getLogger(HealthCheckApiApplication.class);

    @GetMapping("/healthz")
    public void checkDBConection(HttpServletRequest request, @RequestParam(required = false) String requestBody, @RequestHeader(value = HttpHeaders.CONTENT_LENGTH, required = false) Long contentLength, HttpServletResponse response) {

        LOGGER.info("checking the DB Connection");
        if ((requestBody != null && !requestBody.isEmpty()) || (contentLength != null && contentLength != 0L) || request.getParameterMap().size() > 0 || request.getHeader("Authorization") != null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        if (!healthCheckService.isDBConnected()) {
            LOGGER.error("DB service Unavailable");
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
        LOGGER.info("Service Active");
        setHeaders(response);
    }

    @RequestMapping(path = "/healthz", method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.TRACE})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void unSupportedMethods(HttpServletResponse response) {
        LOGGER.error("Requested method is not unsupported");
        setHeaders(response);
    }

    @RequestMapping("/**")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void unknownURL(HttpServletResponse response) {
        LOGGER.error("Unknown URL Path Requested");
        setHeaders(response);
    }

    public void setHeaders(HttpServletResponse response) {
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("Pragma", "no-cache");
    }


}
