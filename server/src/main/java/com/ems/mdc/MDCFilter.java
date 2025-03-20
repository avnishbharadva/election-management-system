package com.ems.mdc;

import com.ems.config.MyUserDetailService;
import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class MDCFilter implements Filter {

    private static final String REQUEST_ID = "requestId";
    private static final String USERNAME = "userName";
    MyUserDetailService myUserDetailService = new MyUserDetailService();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            String requestId = UUID.randomUUID().toString();
            MDC.put(REQUEST_ID, requestId);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            MDC.put(USERNAME,authentication.getName());

            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.clear();
        }
    }
}
