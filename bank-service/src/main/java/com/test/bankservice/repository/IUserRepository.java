package com.test.bankservice.repository;

import com.test.bankservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {

    public User findByUsername(String username);
}
