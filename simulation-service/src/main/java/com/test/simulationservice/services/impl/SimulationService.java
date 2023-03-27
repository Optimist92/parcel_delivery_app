package com.test.simulationservice.services.impl;

import com.test.simulationservice.payload.SimulationParams;
import com.test.simulationservice.payload.SimulationParams2;
import com.test.simulationservice.services.IBankSimulationService;
import com.test.simulationservice.services.ICustomerSimulationService;
import com.test.simulationservice.services.ISimulationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.math.DoubleRange;
import org.apache.commons.lang.math.LongRange;
import org.springframework.stereotype.Service;
import payload.BankSimulationParams;
import payload.CustomerSimulationParams;

import static java.lang.Thread.sleep;

@Service
@RequiredArgsConstructor
public class SimulationService implements ISimulationService {

    private final ICustomerSimulationService customerSimulationService;
    private final IBankSimulationService bankSimulationService;

    private static LongRange customerGenerationDelay;
    private static LongRange customerStartActionDelay;
    private static DoubleRange customerDeclineOrderProbability;

    private static LongRange accountFundsReservationDelay;
    private static LongRange accountFundsChargingDelay;
    private static DoubleRange accountInsufficientFundsProbability;
    private static DoubleRange accountBlockedProbability;
    private static DoubleRange accountNotFoundProbability;


    @Override
    public SimulationParams updateSimulationParams(SimulationParams2 params) {

        customerGenerationDelay = params.getCustomerSimulationParams().getCustomerGenerationDelay();
        customerStartActionDelay = params.getCustomerSimulationParams().getCustomerStartActionDelay();
        customerDeclineOrderProbability = params.getCustomerSimulationParams().getCustomerDeclineOrderProbability();

        accountFundsReservationDelay = params.getBankSimulationParams().getAccountFundsReservationDelay();
        accountFundsChargingDelay = params.getBankSimulationParams().getAccountFundsChargingDelay();
        accountInsufficientFundsProbability = params.getBankSimulationParams().getAccountInsufficientFundsProbability();
        accountBlockedProbability = params.getBankSimulationParams().getAccountBlockedProbability();
        accountNotFoundProbability = params.getBankSimulationParams().getAccountNotFoundProbability();

        //updating params in related services
        bankSimulationService.updateBankSimulationParams(params.getBankSimulationParams());
        customerSimulationService.updateCustomerSimulationParams(params.getCustomerSimulationParams());

        return getParams();
    }

    @Override
    public SimulationParams readSimulationParams() {
        return getParams();
    }

    @Override
    public Boolean readSimulationState() {
        return customerSimulationService.readCustomerSimulationState();
    }

    @Override
    public SimulationParams startSimulation() {

        customerSimulationService.startCustomerSimulation();

        return getParams();
    }

    @Override
    public SimulationParams stopSimulation() {

        customerSimulationService.stopCustomerSimulation();

        return getParams();
    }

    private SimulationParams getParams() {
        return new SimulationParams(
                new CustomerSimulationParams(
                        customerGenerationDelay,
                        customerStartActionDelay,
                        customerDeclineOrderProbability
                ),
                new BankSimulationParams(
                        accountFundsReservationDelay,
                        accountFundsChargingDelay,
                        accountInsufficientFundsProbability,
                        accountBlockedProbability,
                        accountNotFoundProbability
                )
        );
    }
}
