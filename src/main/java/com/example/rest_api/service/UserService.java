package com.example.rest_api.service;

import com.example.rest_api.exceptions.ResourceNotFoundException;
import com.example.rest_api.model.UserEntity;
import com.example.rest_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity createUser(UserEntity newUser){
        UserEntity createdUser = userRepository.save(newUser);
        return ResponseEntity.ok(createdUser);
    }

    //get all available users
    public ResponseEntity<List<UserEntity>> getAllUsers(){

        List<UserEntity> usersList = userRepository.findAll();
        return ResponseEntity.ok(usersList);
    }

    //get a single user
    public ResponseEntity<UserEntity> getUserById(@PathVariable Integer id){
        UserEntity foundUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User does not exist with id : "+ id));
        return ResponseEntity.ok(foundUser);
    }
    //update an existing user
    public ResponseEntity<UserEntity> updateUser(@PathVariable Integer id, @RequestBody
    UserEntity userEntityDetails){


        UserEntity toBeUpdated = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User does not exist with id : "+ id));

        toBeUpdated.setFirstName(userEntityDetails.getFirstName());
        toBeUpdated.setLastName(userEntityDetails.getLastName());
        toBeUpdated.setUsername(userEntityDetails.getUsername());
        toBeUpdated.setEmail(userEntityDetails.getEmail());
        toBeUpdated.setPhoneNumber(userEntityDetails.getPhoneNumber());

        userRepository.save(toBeUpdated);
        return ResponseEntity.ok(toBeUpdated);
    }

    // deleting an existing user

    public ResponseEntity<String> deleteUser(@PathVariable Integer id){

        UserEntity toBeDeletedUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User does not exist with id : "+ id));
        userRepository.delete(toBeDeletedUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
