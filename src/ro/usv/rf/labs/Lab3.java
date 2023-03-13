package ro.usv.rf.labs;

import ro.usv.rf.classes.DistanceMatrix;
import ro.usv.rf.utils.DataUtils;
import ro.usv.rf.utils.FileUtils;

public class Lab3 {
    public static void main(String[] args) {

        double[][] dataSet = FileUtils.readLearningSetFromFile("metrics.txt");
        DataUtils.printMatrix(dataSet);

        DistanceMatrix distanceMatrix = new DistanceMatrix(dataSet);
        System.out.println("=================================");
        System.out.println(distanceMatrix);

        System.out.println("=====================================================");
        double[][] neighbors = distanceMatrix.neighbors(1);
        DataUtils.printMatrix(neighbors);
        System.out.println("=====================================================");


    }
}
