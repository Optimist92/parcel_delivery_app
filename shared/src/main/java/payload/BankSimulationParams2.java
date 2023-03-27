package payload;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.apache.commons.lang.math.DoubleRange;
import org.apache.commons.lang.math.LongRange;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankSimulationParams2 implements Serializable {
    @NonNull
    private Long accountFundsReservationMinDelay;
    @NonNull
    private Long accountFundsReservationMaxDelay;

    @NonNull
    private Long accountFundsChargingMinDelay;
    @NonNull
    private Long accountFundsChargingMaxDelay;

    @NonNull
    private Double accountInsufficientFundsMinProbability;
    @NonNull
    private Double accountInsufficientFundsMaxProbability;

    @NonNull
    private Double accountBlockedMinProbability;
    @NonNull
    private Double accountBlockedMaxProbability;

    @NonNull
    private Double accountNotFoundMinProbability;
    @NonNull
    private Double accountNotFoundMaxProbability;

    public LongRange getAccountFundsReservationDelay() {
        return new LongRange(accountFundsReservationMinDelay, accountFundsReservationMaxDelay);
    }

    public LongRange getAccountFundsChargingDelay() {
        return new LongRange(accountFundsChargingMinDelay, accountFundsChargingMaxDelay);
    }

    public DoubleRange getAccountInsufficientFundsProbability() {
        return new DoubleRange(accountInsufficientFundsMinProbability, accountInsufficientFundsMaxProbability);
    }

    public DoubleRange getAccountBlockedProbability() {
        return new DoubleRange(accountBlockedMinProbability, accountBlockedMaxProbability);
    }

    public DoubleRange getAccountNotFoundProbability() {
        return new DoubleRange(accountNotFoundMinProbability, accountNotFoundMaxProbability);
    }


    /*
    public BankSimulationParams(
            @JsonProperty("accountFundsReservationDelay") LongRange accountFundsReservationDelay,
            @JsonProperty("accountFundsChargingDelay") LongRange accountFundsChargingDelay,
            @JsonProperty("accountInsufficientFundsProbability") DoubleRange accountInsufficientFundsProbability,
            @JsonProperty("accountBlockedProbability") DoubleRange accountBlockedProbability,
            @JsonProperty("accountNotFoundProbability") DoubleRange accountNotFoundProbability
    ) {
        this.accountFundsReservationDelay = accountFundsReservationDelay;
        this.accountFundsChargingDelay = accountFundsChargingDelay;
        this.accountInsufficientFundsProbability = accountInsufficientFundsProbability;
        this.accountBlockedProbability = accountBlockedProbability;
        this.accountNotFoundProbability = accountNotFoundProbability;
    }*/
}