package payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.apache.commons.lang.math.DoubleRange;
import org.apache.commons.lang.math.LongRange;

import java.io.Serializable;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankSimulationParams implements Serializable {
    @NonNull
    @JsonSerialize(as = LongRange.class)
    @JsonDeserialize(as = LongRange.class)
    private LongRange accountFundsReservationDelay;
    @NonNull
    @JsonSerialize(as = LongRange.class)
    @JsonDeserialize(as = LongRange.class)
    private LongRange accountFundsChargingDelay;
    @NonNull
    @JsonSerialize(as = DoubleRange.class)
    @JsonDeserialize(as = DoubleRange.class)
    private DoubleRange accountInsufficientFundsProbability;
    @NonNull
    @JsonSerialize(as = DoubleRange.class)
    @JsonDeserialize(as = DoubleRange.class)
    private DoubleRange accountBlockedProbability;
    @NonNull
    @JsonSerialize(as = DoubleRange.class)
    @JsonDeserialize(as = DoubleRange.class)
    private DoubleRange accountNotFoundProbability;

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