package util;

import org.apache.commons.lang.math.LongRange;

public class DelayService {
    public static Long getDelay(Long minDelay, Long maxDelay) {
        Double delay = ((Math.random() * (maxDelay-minDelay)) + minDelay);
        return delay.longValue();
    }

    public static Long getDelay(LongRange delayRange) {
        Double delay = ((Math.random() * (delayRange.getMaximumLong()-delayRange.getMinimumLong())) + delayRange.getMinimumLong());
        return delay.longValue();
    }
}
