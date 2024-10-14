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
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.*;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity createUser(UserEntity newUser){

        validateEmailandPhoneNumber(newUser);
        existingUserCheck(newUser);

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
        validateEmailandPhoneNumber(userEntityDetails);
        existingUserCheck(userEntityDetails);
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

    private void validateEmailandPhoneNumber(UserEntity userEntity){
        //Regex's from https://regex101.com
        Pattern phonePattern = Pattern.compile("^(\\(\\+[0-9]{2}\\))?([0-9]{3}-?)?([0-9]{3})\\-?([0-9]{4})(\\/[0-9]{4})?$");
        Matcher phoneMatcher = phonePattern.matcher(userEntity.getPhoneNumber());
        Pattern emailPattern = Pattern.compile("^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$");
        Matcher emailMatcher = emailPattern.matcher(userEntity.getEmail());
        if(!phoneMatcher.matches()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Phone Number");
        }else if(!emailMatcher.matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Email Address");
        }
    }
    private void existingUserCheck(UserEntity userEntity){
        if(userRepository.existsByEmail(userEntity.getEmail())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email Already Exists");
        }else if(userRepository.existsByUsername(userEntity.getUsername())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username Already Exists");
        }

    }

}
