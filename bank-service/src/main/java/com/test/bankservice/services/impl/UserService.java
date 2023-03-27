package com.test.bankservice.services.impl;

import com.test.bankservice.domain.Customer;
import com.test.bankservice.domain.User;
import com.test.bankservice.domain.enums.EUserRole;
import com.test.bankservice.repository.IUserRepository;
import com.test.bankservice.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Override
    public List<User> getAllContractors() {
        return userRepository.findAll().stream().filter(user -> user.getRole().equals(EUserRole.ROLE_CONTRACTOR)).collect(Collectors.toList());
    }

    public Optional<User> findByUsername(String username) {
        var user = userRepository.findByUsername(username);

        if (user.getRole() == EUserRole.ROLE_CUSTOMER) {
            user = (Customer)user;
        }

        return Optional.ofNullable(user);
    }
}
