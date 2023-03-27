package com.test.bankservice.services;

import com.test.bankservice.domain.Operation;
import com.test.bankservice.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

public interface IOperationFacade {

    public Operation createReservationWithCard(User initiator, String cardNumber, BigDecimal amountToReserve);
}
