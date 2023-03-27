package com.test.bankservice.services.impl;

import com.test.bankservice.domain.Account;
import com.test.bankservice.domain.Operation;
import com.test.bankservice.domain.User;
import com.test.bankservice.domain.enums.EOperationType;
import com.test.bankservice.payload.operations.CardReservePayload;
import com.test.bankservice.repository.IOperationRepository;
import com.test.bankservice.services.IOperationService;
import com.test.bankservice.services.impl.AccountService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Validated
public class OperationService implements IOperationService {

    private final IOperationRepository operationRepository;

    public Operation createOperation(User initiator, EOperationType type, BigDecimal amount, Account accountFrom, Account accountTo) {

        var operation = new Operation();
        operation.setInitiator(initiator);
        operation.setType(type);
        operation.setAmount(amount);
        operation.setAccountFrom(accountFrom);
        operation.setAccountTo(accountTo);

        return operationRepository.save(operation);
    }
}
