package com.test.bankservice.services.impl;

import com.test.bankservice.services.ISimulationService;
import org.springframework.stereotype.Service;
import payload.BankSimulationParams;
import payload.BankSimulationParams2;
import util.DelayService;
import util.ProbabilityService;
import org.apache.commons.lang.math.DoubleRange;
import org.apache.commons.lang.math.LongRange;
import java.math.BigDecimal;

import static java.lang.Thread.sleep;

@Service
public class SimulationService implements ISimulationService {

    private static LongRange accountFundsReservationDelay;
    private static LongRange accountFundsChargingDelay;
    private static DoubleRange accountInsufficientFundsProbability;
    private static DoubleRange accountBlockedProbability;
    private static DoubleRange accountNotFoundProbability;

    public BankSimulationParams updateSimulationParams(BankSimulationParams2 params) {

        accountFundsReservationDelay = params.getAccountFundsReservationDelay();
        accountFundsChargingDelay = params.getAccountFundsChargingDelay();
        accountInsufficientFundsProbability = params.getAccountInsufficientFundsProbability();
        accountBlockedProbability = params.getAccountBlockedProbability();
        accountNotFoundProbability = params.getAccountNotFoundProbability();

        return getParams();
    }

    public BankSimulationParams readSimulationParams() {

        return getParams();
    }

    public BigDecimal reserveAmount(BigDecimal amountToReserve) {

        try {
            sleep(DelayService.getDelay(accountFundsReservationDelay));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        var hit = Math.random();

        if (accountInsufficientFundsProbability.containsDouble(hit)) {
            throw new RuntimeException("account insufficient funds");
        }

        if (accountBlockedProbability.containsDouble(hit)) {
            throw new RuntimeException("account blocked");
        }

        if (accountNotFoundProbability.containsDouble(hit)) {
            throw new RuntimeException("account not found");
        }

        return amountToReserve;
    }

    public BigDecimal chargeAmount(BigDecimal amountToCharge) {

        try {
            sleep(DelayService.getDelay(accountFundsChargingDelay));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        var hit = Math.random();

        if (accountBlockedProbability.containsDouble(hit)) {
            throw new RuntimeException("account blocked");
        }

        return amountToCharge;
    }

    private BankSimulationParams getParams() {

        return new BankSimulationParams(
                accountFundsReservationDelay,
                accountFundsChargingDelay,
                accountInsufficientFundsProbability,
                accountBlockedProbability,
                accountNotFoundProbability);
    }
}
