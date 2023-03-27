package com.test.bankservice.services;

import com.test.bankservice.domain.User;
import com.test.bankservice.repository.IUserRepository;

import java.util.Optional;

public interface ICustomerService {

    public Optional<User> findByUsername(String username);
}
