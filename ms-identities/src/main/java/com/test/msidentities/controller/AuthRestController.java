package com.test.msidentities.controller;

import com.test.msidentities.model.User;
import com.test.msidentities.service.AuthenticationService;
import dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.test.msidentities.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthRestController {

    private final AuthenticationService service;

    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse token = service.authenticate(request);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegistrationRequest request) {
        User user = service.register(request);
        log.info(request.getUsername() + " registered");
        return new ResponseEntity<>(userMapper.entityToDto(user), HttpStatus.OK);
    }

    @PostMapping("/courier/register")
    public ResponseEntity<String> courierRegister(@RequestBody RegistrationRequest request) {
        String response = service.courierRegister(request);
        log.info("Courier " + request.getUsername() + " registered");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{public_id}")
    public ResponseEntity<String> findUserByPublicId(@PathVariable String public_id) {
        return ResponseEntity.ok().body(service.getUserByPublicId(public_id).getUsername());
    }
}
