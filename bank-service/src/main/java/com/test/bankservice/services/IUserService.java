package com.test.bankservice.services;

import com.test.bankservice.domain.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllContractors();

    public Optional<User> findByUsername(String username);
}
