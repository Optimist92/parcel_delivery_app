package com.test.msidentities.service;

import com.test.msidentities.controller.AuthenticationRequest;
import com.test.msidentities.controller.AuthenticationResponse;
import com.test.msidentities.controller.RegistrationRequest;
import com.test.msidentities.exception.IncorrectPasswordException;
import com.test.msidentities.model.User;
import com.test.msidentities.repository.UserRepository;
import com.test.msidentities.util.JwtUtil;
import dto.OrderDTO;
import dto.UserDTO;
import enums.EUserRole;
import exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtUtil jwtService;

    public User register(RegistrationRequest request) {
        User customer = new User();
        customer.setEmail(request.getEmail());
        customer.setPassword(passwordEncoder().encode(request.getPassword()));
        customer.setUsername(request.getUsername());
        customer.setRole(EUserRole.ROLE_CUSTOMER);
        userRepository.save(customer);
        return userRepository.save(customer);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new IncorrectPasswordException("Неверное имя пользователя или пароль"));
        if (!passwordEncoder().matches(request.getPassword(), user.getPassword())) {
            throw new IncorrectPasswordException("Неверное имя пользователя или пароль");
        }
        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("Role", user.getRole().name());
        roleMap.put("PublicId", user.getPublicId().toString());
        return AuthenticationResponse.builder().token(jwtService.generateToken(roleMap, UserDetailsImpl.build(user))).build();
    }

    public String courierRegister(RegistrationRequest request) {
        User courier = new User();
        courier.setEmail(request.getEmail());
        courier.setPassword(passwordEncoder().encode(request.getPassword()));
        courier.setUsername(request.getUsername());
        courier.setRole(EUserRole.ROLE_COURIER);
        userRepository.save(courier);
        return String.format("Курьер '%s' зарегистрирован", courier.getUsername());
    }

    public User getUserByPublicId(String publicId) {
        return userRepository.findByPublicId(Long.getLong(publicId)).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
