package com.test.simulationservice.services;


import payload.BankSimulationParams2;

public interface IBankSimulationService {

    public BankSimulationParams2 updateBankSimulationParams(BankSimulationParams2 params);

    public BankSimulationParams2 readBankSimulationParams();
}
