package ro.usv.rf.utils;

import java.util.Arrays;
import java.util.stream.IntStream;

public class DataUtils {
    public static void printMatrix(double[][] x) {
        for (double[] lin : x) {
            for (double xcrt : lin)
                System.out.print(xcrt + "\t");
            System.out.println();
        }
    }

    public static double[][] normalizeLearningSet(double[][] patternSet){
        double[][] normalizePatternSet =  new double[patternSet.length][patternSet[0].length];
        double[] max = new double[patternSet[0].length];
        double[] min = new double[patternSet[0].length];

        for (int j=0; j<patternSet[0].length; j++){
            max[j] = patternSet[0][j];
            min[j] = patternSet[0][j];
            for (double[] doubles : patternSet) {
                if (max[j] < doubles[j])
                    max[j] = doubles[j];
                min[j] = Math.min(doubles[j], min[j]);
            }
        }
        for (int i = 0; i < patternSet.length; i++) {
            for (int j = 0; j < patternSet[0].length; j++) {
                normalizePatternSet[i][j] =(patternSet[i][j]- min[j])/(max[j]-min[j]);
            }
        }

        return normalizePatternSet;
    }

    public static void printVector(double[] vector){
        for (int i = 0; i < vector.length; i++) {
            System.out.println("v["+ i +"]= "+ vector[i]);
        }
    }

}
