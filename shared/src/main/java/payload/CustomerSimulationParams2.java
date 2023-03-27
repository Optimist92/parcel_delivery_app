package payload;

import lombok.*;
import org.apache.commons.lang.math.DoubleRange;
import org.apache.commons.lang.math.LongRange;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSimulationParams2 implements Serializable {

    @NonNull
    private Long customerGenerationMinDelay;
    @NonNull
    private Long customerGenerationMaxDelay;

    @NonNull
    private Long customerStartActionMinDelay;
    @NonNull
    private Long customerStartActionMaxDelay;

    @NonNull
    private Double customerDeclineOrderMinProbability;
    @NonNull
    private Double customerDeclineOrderMaxProbability;

    public LongRange getCustomerGenerationDelay() {
        return new LongRange(customerGenerationMinDelay, customerGenerationMaxDelay);
    }

    public LongRange getCustomerStartActionDelay() {
        return new LongRange(customerStartActionMinDelay, customerStartActionMaxDelay);
    }

    public DoubleRange getCustomerDeclineOrderProbability() {
        return new DoubleRange(customerDeclineOrderMinProbability, customerDeclineOrderMaxProbability);
    }
}
