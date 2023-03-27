package com.test.bankservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OperationDTO {

    private UUID id;

    private UUID publicID;

    private String cardNumber;

    private BigDecimal amount;
}
