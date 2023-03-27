package com.test.msidentities.service;

import com.test.msidentities.controller.AuthenticationRequest;
import com.test.msidentities.controller.AuthenticationResponse;
import com.test.msidentities.controller.RegistrationRequest;
import com.test.msidentities.exception.IncorrectPasswordException;
import com.test.msidentities.model.User;
import com.test.msidentities.repository.UserRepository;
import com.test.msidentities.util.JwtUtil;
import dto.CourierDTO;
import enums.EUserRole;
import exception.EntityNotFoundException;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    private final UserRepository userRepository;
    private final JwtUtil jwtService;

    private final RestTemplate restTemplate;

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
    @Transactional
    public String courierRegister(RegistrationRequest request) {
        User courier = new User();
        courier.setEmail(request.getEmail());
        courier.setPassword(passwordEncoder().encode(request.getPassword()));
        courier.setUsername(request.getUsername());
        courier.setRole(EUserRole.ROLE_COURIER);
        //courier = userRepository.saveAndFlush(courier);
        courier = saveUser(courier);
        //User savedCourier = userRepository.findById(courier.getId()).orElseThrow();
        CourierDTO courierDTO = new CourierDTO();
        courierDTO.setUserPublicId(courier.getPublicId());
        courierDTO.setIsAvailable(false);
        ResponseEntity<CourierDTO> response = restTemplate.postForEntity("http://localhost:8083/v1/couriers", courierDTO, CourierDTO.class);
        if(response.getBody() == null) {
            throw new RuntimeException("Проблемы на стороне сервера курьеров");
        }
        return String.format("Курьер №'%d' зарегистрирован", courier.getPublicId());
    }

    @Transactional
    public User saveUser(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(user);
        transaction.commit();
        entityManager.refresh(user);
        entityManager.find(User.class, user.getId());
        return entityManager.find(User.class, user.getId());
    }

    public User getUserByPublicId(String publicId) {
        return userRepository.findByPublicId(Long.getLong(publicId)).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
