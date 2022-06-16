package com.example.appweather.microservice.controller;

import com.example.appweather.microservice.config.CustomAuthenticationManager;
import com.example.appweather.microservice.model.Agent;
import com.example.appweather.microservice.repository.AgentRepository;
import com.example.appweather.microservice.service.AgentService;
import com.example.appweather.model.Role;
import com.example.appweather.model.User;
import com.example.appweather.response.BaseResponse;
import com.example.appweather.security.JwtTokenProvider;
import com.example.appweather.service.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/agent")
public class AgentController {

    @GetMapping("/1")
    public ResponseEntity<?> getMessage1(){
        String authentication = UserUtil.getLoginFromAuthentication();
        log.info("AgentController: method1 called " + authentication);
        return ResponseEntity.status(HttpStatus.OK).body("OK1");
    }

    @GetMapping("/2")
    public ResponseEntity<?> getMessage2(){
        String authentication = UserUtil.getLoginFromAuthentication();
        log.info("AgentController: method2 called " + authentication);
        return ResponseEntity.status(HttpStatus.OK).body("OK2");
    }

    @GetMapping("/3")
    public ResponseEntity<?> getMessage3(){
        String authentication = UserUtil.getLoginFromAuthentication();
        log.info("AgentController: method3 called " + authentication);
        return ResponseEntity.status(HttpStatus.OK).body("OK3");
    }

}
