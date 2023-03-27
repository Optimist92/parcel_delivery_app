package com.test.simulationservice.services;

import payload.CustomerSimulationParams;
import payload.CustomerSimulationParams2;

public interface ICustomerSimulationService {

    public CustomerSimulationParams updateCustomerSimulationParams(CustomerSimulationParams2 params);

    public CustomerSimulationParams readCustomerSimulationParams();

    public Boolean readCustomerSimulationState();

    public CustomerSimulationParams startCustomerSimulation();

    public CustomerSimulationParams stopCustomerSimulation();
}
