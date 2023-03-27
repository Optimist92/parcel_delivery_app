package com.test.bankservice.repository;

import com.test.bankservice.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAccountRepository extends JpaRepository<Account, UUID> {

}
