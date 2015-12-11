package com.splunk.hollywood.aop;

import com.splunk.hollywood.constants.Header;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    private static final String HARD_CODED_TOKEN = "ca$hc0w";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(Header.TOKEN);
        return token == null || token.equals(HARD_CODED_TOKEN);
    }
}
