package com.test.bankservice.services.impl;

import com.test.bankservice.domain.Account;
import com.test.bankservice.domain.Operation;
import com.test.bankservice.domain.User;
import com.test.bankservice.domain.enums.EOperationType;
import com.test.bankservice.services.IAccountService;
import com.test.bankservice.services.ICardService;
import com.test.bankservice.services.IOperationFacade;
import com.test.bankservice.services.IOperationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Validated
public class OperationFacade implements IOperationFacade {

    private final ICardService cardService;
    private final IAccountService accountService;
    private final IOperationService operationService;

    @Transactional(propagation = Propagation.REQUIRED)
    public Operation createReservationWithCard(User initiator, String cardNumber, BigDecimal amountToReserve) {

        var card = cardService.getCardByNumber(cardNumber).orElseThrow(() -> new RuntimeException("no card with number " + cardNumber));
        var targetAccount = card.getAccount();

        accountService.reserveAmount(targetAccount, amountToReserve);
        return operationService.createOperation(initiator, EOperationType.TYPE_CREATE_RESERVE, amountToReserve, targetAccount, null);
    }
}
