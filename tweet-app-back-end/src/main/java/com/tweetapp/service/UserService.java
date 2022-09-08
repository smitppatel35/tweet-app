package com.tweetapp.service;

import com.tweetapp.dtos.UserRequest;
import com.tweetapp.dtos.request.ForgotPasswordRequest;
import com.tweetapp.dtos.request.LoginRequest;
import com.tweetapp.dtos.request.RegistrationRequest;
import com.tweetapp.dtos.response.UserDTO;
import com.tweetapp.exception.ResourceAlreadyExistsException;
import com.tweetapp.exception.ResourceNotFoundException;
import com.tweetapp.model.UserEntity;

import java.util.List;

public interface UserService {
    // Register User
    void register(UserRequest registrationRequest) throws ResourceAlreadyExistsException;

    // Login User
    UserDTO login(LoginRequest loginRequest) throws ResourceNotFoundException;

    // get all User
    List<UserDTO> getAllUsers();

    // forgot password
    void forgot(String username, ForgotPasswordRequest forgotPasswordRequest) throws ResourceNotFoundException;

    // search User
    List<UserDTO> search(String username) throws ResourceNotFoundException;

    UserEntity validateUsername(String username) throws ResourceNotFoundException;
}
