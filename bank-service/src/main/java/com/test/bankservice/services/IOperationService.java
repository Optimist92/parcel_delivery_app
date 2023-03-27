package com.test.bankservice.services;

import com.test.bankservice.domain.Account;
import com.test.bankservice.domain.Operation;
import com.test.bankservice.domain.User;
import com.test.bankservice.domain.enums.EOperationType;

import java.math.BigDecimal;

public interface IOperationService {

    Operation createOperation(User initiator, EOperationType type, BigDecimal amount, Account accountFrom, Account accountTo);
}
