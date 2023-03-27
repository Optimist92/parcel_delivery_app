package com.test.simulationservice.services;

import com.test.simulationservice.payload.SimulationParams;
import com.test.simulationservice.payload.SimulationParams2;

public interface ISimulationService {

    public SimulationParams updateSimulationParams(SimulationParams2 params);

    public SimulationParams readSimulationParams();

    public Boolean readSimulationState();

    public SimulationParams startSimulation();

    public SimulationParams stopSimulation();
}
