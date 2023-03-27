package com.test.bankservice.payload.operations;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CardReservePayload {
    private String cardNumber;

    private BigDecimal Amount;

    private UUID orderPublicId;
}
