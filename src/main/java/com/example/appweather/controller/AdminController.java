package com.example.appweather.controller;

import com.example.appweather.model.User;
import com.example.appweather.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class AdminController {
    final UserService userService;
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody User user) {
        return userService.add(user);
    }

    @GetMapping("")
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping("/change")
    public ResponseEntity<?> change(@RequestBody User user){
        return userService.change(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return userService.deleteById(id);
    }

}
