package com.test.simulationservice.payload;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import payload.BankSimulationParams2;
import payload.CustomerSimulationParams2;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimulationParams2 implements Serializable {

    @JsonSerialize(as = CustomerSimulationParams2.class)
    @JsonDeserialize(as = CustomerSimulationParams2.class)
    private CustomerSimulationParams2 customerSimulationParams;

    @JsonSerialize(as = BankSimulationParams2.class)
    @JsonDeserialize(as = BankSimulationParams2.class)
    private BankSimulationParams2 bankSimulationParams;

    /*
    private LongRange accountFundsReservationDelay;
    private LongRange accountFundsChargingDelay;

    private DoubleRange accountInsufficientFundsProbability;
    private DoubleRange accountBlockedProbability;
    private DoubleRange accountNotFoundProbability;
*/

    //private Long customerPool
}
