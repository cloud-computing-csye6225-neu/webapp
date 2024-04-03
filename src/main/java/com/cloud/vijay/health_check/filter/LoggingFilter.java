package com.cloud.vijay.health_check.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.ThreadContext;

import java.io.IOException;
import java.net.InetAddress;
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // For example, you can manipulate the request or response
        // or perform some validation
        System.out.println("Filtering request");
        ThreadContext.put("httpMethod", request.getMethod());
        ThreadContext.put("path", request.getRequestURI());
        ThreadContext.put("remoteAddress", request.getRemoteAddr());
        ThreadContext.put("serverIP", InetAddress.getLocalHost().getHostAddress());
        // Proceed with the filter chain
        chain.doFilter(request, response);
        ThreadContext.clearAll();
        System.out.println("Filtering request ends");
    }

}
