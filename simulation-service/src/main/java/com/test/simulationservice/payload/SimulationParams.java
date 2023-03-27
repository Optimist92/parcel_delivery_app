package com.test.simulationservice.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import payload.BankSimulationParams;
import payload.CustomerSimulationParams;

@Getter
@Setter
@AllArgsConstructor
public class SimulationParams {

    private CustomerSimulationParams customerSimulationParams;

    private BankSimulationParams bankSimulationParams;

    /*
    private LongRange accountFundsReservationDelay;
    private LongRange accountFundsChargingDelay;

    private DoubleRange accountInsufficientFundsProbability;
    private DoubleRange accountBlockedProbability;
    private DoubleRange accountNotFoundProbability;
*/

    //private Long customerPool
}
