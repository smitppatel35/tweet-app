package com.tweetapp.controller;

import com.tweetapp.dtos.SuccessResponse;
import com.tweetapp.dtos.request.ForgotPasswordRequest;
import com.tweetapp.dtos.request.LoginRequest;
import com.tweetapp.dtos.request.RegistrationRequest;
import com.tweetapp.dtos.response.UserDTO;
import com.tweetapp.exception.ResourceAlreadyExistsException;
import com.tweetapp.exception.ResourceNotFoundException;
import com.tweetapp.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/tweets")
@Tag(name = "User", description = "Manage Users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegistrationRequest registrationRequest)
            throws ResourceAlreadyExistsException {
        userService.register(registrationRequest);
        return new ResponseEntity<>(new SuccessResponse("Register"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginRequest loginRequest)
            throws ResourceNotFoundException {
        return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
    }

    @GetMapping("/users/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/{username}/forgot")
    public ResponseEntity<Void> forgot(@PathVariable("username") String username, @RequestBody ForgotPasswordRequest forgotPasswordRequest)
            throws ResourceNotFoundException {
        userService.forgot(username, forgotPasswordRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<List<UserDTO>> search(@PathVariable("username") String username) throws ResourceNotFoundException {
        return new ResponseEntity<>(userService.search(username), HttpStatus.OK);
    }
}
