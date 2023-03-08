package ro.usv.rf.utils;

import ro.usv.rf.classes.Pattern;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class StatisticsUtils {

    public static double calculateFeatureAverage(double[] feature) {
//        return Arrays.stream(feature).average().getAsDouble();
        double average = 0.0;
        for (double v : feature) {
            average += v;
        }
        average = average / feature.length;
        return average;
    }


    public static Map<Pattern, Double> getPatternsMapFromInitialSet(double[][] patternSet) {
        Map<Pattern, Double> patternsMap = new HashMap<>();
        for (double[] line : patternSet) {
            Pattern pattern = new Pattern(line);
            if (patternsMap.containsKey(pattern)) {
                double current = patternsMap.get(pattern);
                patternsMap.put(pattern, current + 1);
            } else {
                patternsMap.put(pattern, 1.);
            }
        }
        return patternsMap;
    }

    public static double[] calculateWeightedAverages(Map<Pattern, Double> patternsMap, int numberOfFeatures) {
        double[] weightedAverages = new double[numberOfFeatures];
        double totalWeight = 0;
        for (Map.Entry<Pattern, Double> entry : patternsMap.entrySet()) {
            Pattern pattern = entry.getKey();
            double weight = entry.getValue();
            double[] patternValues = pattern.getPatternValues();
            totalWeight += weight;
            for (int i = 0; i < numberOfFeatures; i++) {
                weightedAverages[i] += weight * patternValues[i];
            }
        }
        for (int i = 0; i < numberOfFeatures; i++) {
            weightedAverages[i] = weightedAverages[i] / totalWeight;
        }
        return weightedAverages;
    }

    public static double[] calculateFeatureDispersion(Map<Pattern, Double> patternsMap, int numberOfFeatures, double[] avg) {
        double[] dispersion = new double[numberOfFeatures];
        double totalWeight = 0;
        for (Map.Entry<Pattern, Double> entry : patternsMap.entrySet()) {
            Pattern pattern = entry.getKey();
            double weight = entry.getValue();
            double[] patternValues = pattern.getPatternValues();
            totalWeight += weight;
            for (int i = 0; i < numberOfFeatures; i++) {
                dispersion[i] += (patternValues[i] - avg[i]) * (patternValues[i] - avg[i]) * entry.getValue();
            }
        }
        for (int i = 0; i < numberOfFeatures; i++) {
            dispersion[i] = dispersion[i] / (totalWeight - 1);
        }
        return dispersion;
    }


}
