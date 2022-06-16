package com.example.appweather.microservice.config;

import com.example.appweather.microservice.model.Agent;
import com.example.appweather.microservice.service.AgentService;
import com.example.appweather.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationManager implements AuthenticationProvider {

    private AgentService agentService;

    public CustomAuthenticationManager(AgentService agentService) {
        this.agentService = agentService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String nickname = authentication.getName();
        String password = authentication.getCredentials().toString();

        Agent agentDb = agentService.getByNickName(nickname);
        if (agentDb != null){
            boolean pass = agentService.checkPassword(agentDb, password);

            if (pass){
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(agentDb.getRole().getName()));
                UserDetails principalUser = new org.springframework.security.core.userdetails.User(nickname, password, true, true, true, true, authorities);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principalUser, password, authorities));
                return new UsernamePasswordAuthenticationToken(principalUser, password, authorities);
            } else
                throw new BadCredentialsException(":(");


        } else {
            throw new BadCredentialsException(":(");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
