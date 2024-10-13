package com.example.rest_api.controller;

import com.example.rest_api.model.UserEntity;
import com.example.rest_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserEntity userEntity) {
        return userService.createUser(userEntity);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Integer id, @RequestBody UserEntity userEntityDetails){
        return userService.updateUser(id,userEntityDetails);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsers(@PathVariable Integer id){
        return userService.deleteUser(id);
    }


}
