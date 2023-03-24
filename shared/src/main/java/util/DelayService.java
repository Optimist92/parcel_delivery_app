package util;

public class DelayService {
    public static Long getDelay(Long minDelay, Long maxDelay) {
        Double delay = ((Math.random() * (maxDelay-minDelay)) + minDelay);
        return delay.longValue();
    }
}
