package payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang.math.DoubleRange;
import org.apache.commons.lang.math.LongRange;

@Getter
@Setter
@AllArgsConstructor
public class CustomerSimulationParams {

    @NonNull
    private LongRange customerGenerationDelay;
    @NonNull
    private LongRange customerStartActionDelay;
    @NonNull
    private DoubleRange customerDeclineOrderProbability;
}
