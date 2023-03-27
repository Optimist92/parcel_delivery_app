package com.test.bankservice.services;

import com.test.bankservice.domain.Account;

import java.math.BigDecimal;

public interface IAccountService {

    Account reserveAmount(Account account, BigDecimal delta);

    //Account updateAvailableAmount(Account account, BigDecimal delta);

    //Account updateReservedAmount(Account account, BigDecimal delta);
}
