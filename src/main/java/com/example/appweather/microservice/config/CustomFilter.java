package com.example.appweather.microservice.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Component
public class CustomFilter extends GenericFilterBean {

    private final CustomAuthenticationManager customAuthenticationManager;

    public CustomFilter(CustomAuthenticationManager customAuthenticationManager) {
        this.customAuthenticationManager = customAuthenticationManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        if (uri.startsWith("/agent") && !uri.startsWith("/agents")){
            String nickname = req.getParameter("nickname");
            String password = req.getParameter("password");
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(nickname, password);
            Authentication authentication = customAuthenticationManager.authenticate(authReq);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }





}
