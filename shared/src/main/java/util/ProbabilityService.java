package util;

public class ProbabilityService {

    public static Boolean hitProbability(Double probability) {
        return Math.random() < probability;
    }
}
