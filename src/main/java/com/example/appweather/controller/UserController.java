package com.example.appweather.controller;

import com.example.appweather.model.User;
import com.example.appweather.service.UserService;
import com.example.appweather.service.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> get() {
        return userService.get(UserUtil.getLoginFromAuthentication());
    }

    @PostMapping("/change")
    public ResponseEntity<?> change(@RequestBody User user) {
        return userService.change(user, UserUtil.getLoginFromAuthentication());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(){
        return userService.delete(UserUtil.getLoginFromAuthentication());
    }





}
