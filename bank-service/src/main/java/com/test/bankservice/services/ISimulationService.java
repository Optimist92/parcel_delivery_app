package com.test.bankservice.services;

import payload.BankSimulationParams;
import payload.BankSimulationParams2;

import java.math.BigDecimal;

public interface ISimulationService {

    public BankSimulationParams updateSimulationParams(BankSimulationParams2 payload);

    public BankSimulationParams readSimulationParams();

    public BigDecimal reserveAmount(BigDecimal amountToReserve);
}
