package com.example.appweather.microservice.controller;

import com.example.appweather.microservice.config.CustomAuthenticationManager;
import com.example.appweather.microservice.model.Agent;
import com.example.appweather.microservice.service.AgentService;
import com.example.appweather.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
@RequestMapping("/auth")
public class AgentAuthController {

    private AgentService agentService;

    private final CustomAuthenticationManager customAuthenticationManager;

    public AgentAuthController(AgentService agentService, CustomAuthenticationManager customAuthenticationManager) {
        this.agentService = agentService;
        this.customAuthenticationManager = customAuthenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Agent agent) {
        log.info("AgentAuthController: logging in " + agent.getNickName());
        BaseResponse response = agentService.login(agent);
        return ResponseEntity.status(response.isSuccess() ? 200 : 401).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Agent agent) {
        log.info("AgentAuthController: logging out " + agent.getNickName());
        BaseResponse response = agentService.logout(agent);
        return ResponseEntity.status(response.isSuccess() ? 200 : 401).body(response);
    }
}
