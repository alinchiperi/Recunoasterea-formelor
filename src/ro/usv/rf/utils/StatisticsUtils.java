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
    public static double calculateCovariance(Double[] feature1, Double[] feature2, double feature1WeightedAverage, double feature2WeightedAverage) {
        double covariance;
        double sum = 0;
        for (int i = 0; i < feature1.length; i++) {
            sum += (feature1[i] - feature1WeightedAverage) * (feature2[i] - feature2WeightedAverage);
        }
        covariance = (1.0 / (feature1.length - 1)) * sum;
        return covariance;
    }

    public static double calculateCorrelationCoefficient(double covariance, double feature1Dispersion, double feature2Dispersion) {
        return covariance / Math.sqrt(feature1Dispersion * feature2Dispersion);
    }
    protected static double getSumForDispersion(double[] feature, double featureWeightedAverage) {
        double sum = 0;
        for (double f : feature) {
            sum += Math.pow(f - featureWeightedAverage, 2);
        }
        return sum;
    }


}
