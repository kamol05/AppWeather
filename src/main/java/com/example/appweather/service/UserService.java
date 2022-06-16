package com.example.appweather.service;

import com.example.appweather.model.Role;
import com.example.appweather.model.User;
import com.example.appweather.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements BaseService{
    final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> add(User user) {
        return ResponseEntity.ok().body(userRepository.save(user));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public ResponseEntity<?> getById(Long id) {
        Optional<User> optional = userRepository.findById(id);
        if (!optional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        }
        return ResponseEntity.ok(optional.get());
    }

    public ResponseEntity<?> change(User user) {
        Optional<User> optional = userRepository.findByLogin(user.getLogin());
        if (!optional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        }
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    public ResponseEntity<?> deleteById(Long id) {
        Optional<User> optional = userRepository.findById(id);
        if (!optional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        }
        userRepository.delete(optional.get());
        return ResponseEntity.ok(optional.get());
    }

//------------User Methods--------------
    public ResponseEntity<?> get(String login) {
        Optional<User> optional = userRepository.findByLogin(login);
        return ResponseEntity.ok(optional.get());
    }

    public ResponseEntity<?> change(User user, String login) {
        if ( !user.getLogin().equals(login) || user.getRole() == Role.ADMIN){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You Dont Have Permission!!");
        }
        return ResponseEntity.ok().body(userRepository.save(user));
    }

    public ResponseEntity<?> delete(String userName) {
        System.out.println(userName);
        Optional<User> optional = userRepository.findByLogin(userName);
        return deleteById(optional.get().getId());
    }

}
