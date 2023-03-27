package com.test.bankservice.repository;

import com.test.bankservice.domain.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IOperationRepository extends JpaRepository<Operation, UUID> {
}
