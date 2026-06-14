package com.architecture.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class MethodFilteringFilter implements Filter {

    // Only allow these methods
    private final List<String> ALLOWED_METHODS = Arrays.asList("GET", "POST");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String method = httpRequest.getMethod();

        if (!ALLOWED_METHODS.contains(method)) {
            httpResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            httpResponse.getWriter().write("{\"error\": \"Method " + method + " not allowed\"}");
            return; // Stop the chain, request never hits the controller
        }

        chain.doFilter(request, response);
    }
}