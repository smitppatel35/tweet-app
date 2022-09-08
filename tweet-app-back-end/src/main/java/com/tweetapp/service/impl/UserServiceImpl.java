package com.tweetapp.service.impl;

import com.tweetapp.dtos.UserRequest;
import com.tweetapp.dtos.request.ForgotPasswordRequest;
import com.tweetapp.dtos.request.LoginRequest;
import com.tweetapp.dtos.request.RegistrationRequest;
import com.tweetapp.dtos.response.UserDTO;
import com.tweetapp.exception.ResourceAlreadyExistsException;
import com.tweetapp.exception.ResourceNotFoundException;
import com.tweetapp.model.UserEntity;
import com.tweetapp.repository.UserRepository;
import com.tweetapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void register(UserRequest user) throws ResourceAlreadyExistsException {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(user.getUsername());
        userEntity.setEmail(user.getEmail());
        userEntity.setName(user.getName());
        userEntity.setGender(user.getGender());
        userEntity.setAvatar("https://tweet-app-avatars.s3.ap-south-1.amazonaws.com/"+user.getUsername() + ".svg");
        userEntity.setUserId(extractUserIdFromEmail(user.getEmail()));

        try {
            userRepository.save(userEntity);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistsException("User Already registered!");
        }
    }

    @Override
    public UserDTO login(LoginRequest loginRequest) throws ResourceNotFoundException {
//        UserEntity userEntity = validateUsername(loginRequest.getEmail());
////
////        if (!userEntity.getPassword().equals(loginRequest.getPassword())) {
////            throw new ResourceNotFoundException("Invalid Username or Password!");
////        }
////
        return null;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    @Override
    public void forgot(String username, ForgotPasswordRequest forgotPasswordRequest) throws ResourceNotFoundException {
//        UserEntity userEntity = validateUsername(username);

//        if (!userEntity.getPassword().equals(forgotPasswordRequest.getCurrentPassword())) {
//            throw new ResourceNotFoundException("Invalid Password!");
//        }
//
//        userEntity.setPassword(forgotPasswordRequest.getNewPassword());
//        userRepository.save(userEntity);
//        log.debug("Password reset for User: {}", username);
    }

    @Override
    public List<UserDTO> search(String username) throws ResourceNotFoundException {
        List<UserDTO> searchResults = userRepository.searchByUsername(username).stream().map(this::entityToDTO).collect(Collectors.toUnmodifiableList());

        if (searchResults.isEmpty()) {
            throw new ResourceNotFoundException("No User Found");
        }
        log.debug("{} search result retrieved", searchResults.size());
        return searchResults;
    }

    private UserDTO entityToDTO(UserEntity userEntity) {
        UserDTO dto = new UserDTO();

        dto.setEmail(userEntity.getEmail());
        dto.setFirstName(userEntity.getName());
        dto.setGender(userEntity.getGender());
        dto.setAvatar(userEntity.getAvatar());
        dto.setUsername(userEntity.getUserId());

        return dto;
    }

    @Override
    public UserEntity validateUsername(String username) throws ResourceNotFoundException {
        Optional<UserEntity> optional = userRepository.findById(username);
        if (optional.isPresent()) return optional.get();
        else {
            throw new ResourceNotFoundException("Invalid Username: " + username);
        }
    }

    private String extractUserIdFromEmail(String email) {
        return email.split("@")[0];
    }
}
