package ro.usv.rf.labs;

import ro.usv.rf.classes.Pattern;
import ro.usv.rf.utils.DataUtils;
import ro.usv.rf.utils.FileUtils;
import ro.usv.rf.utils.StatisticsUtils;

import java.util.Arrays;
import java.util.Map;

public class Lab2 {
    public static void main(String[] args) {
        double[][] patternSet = FileUtils.readLearningSetFromFile("in.txt");
        int numberOfPatterns = patternSet.length;
        int numberOfFeatures = patternSet[0].length;

        for (int j=0; j<numberOfFeatures; j++)
        {
            double[] feature = new double[numberOfPatterns];
            for (int i=0; i<numberOfPatterns; i++)
            {
                feature[i] = patternSet[i][j];
            }
            System.out.println("Feature average is:" + StatisticsUtils.calculateFeatureAverage(feature));
        }

        Map<Pattern, Double> patternsMap = StatisticsUtils.getPatternsMapFromInitialSet(patternSet);
        System.out.println(patternsMap);
        double[] weightedAverages = StatisticsUtils.calculateWeightedAverages(patternsMap, numberOfFeatures);
        double[] dispersion = StatisticsUtils.calculateFeatureDispersion(patternsMap,numberOfFeatures, weightedAverages);
        DataUtils.printVector(weightedAverages);
        System.out.println("===============================================");
        DataUtils.printVector(dispersion);
    }

}
