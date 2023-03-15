package ro.usv.rf.labs;

import ro.usv.rf.classes.DistanceMatrix;
import ro.usv.rf.utils.DataUtils;
import ro.usv.rf.utils.DistanceUtils;
import ro.usv.rf.utils.FileUtils;

public class Lab3 {
    public static void main(String[] args) {

        System.out.println("========================DATASET=========================");

        double[][] dataSet = FileUtils.readLearningSetFromFile("metrics.txt");
        DataUtils.printMatrix(dataSet);

        DistanceMatrix distanceMatrix = new DistanceMatrix(dataSet);
        System.out.println("==================EUCLID DISTANCE====================");
        System.out.println(distanceMatrix);

        System.out.println("==================NEIGHBORS===========================");
        double[][] neighbors = distanceMatrix.neighbors(1);
        DataUtils.printMatrix(neighbors);

        System.out.println("===================HOMEWORK==========================");

        System.out.println("===================EUCLID==========================");
        DistanceMatrix distanceMatrixEuclid = new DistanceMatrix(dataSet, DistanceUtils::distEuclid, false);
        System.out.println(distanceMatrixEuclid);

        System.out.println("===================MANHATTAN==========================");
        DistanceMatrix distanceMatrixManhattan = new DistanceMatrix(dataSet, DistanceUtils::distManhattan, true);
        System.out.println(distanceMatrixManhattan);
        System.out.println("===================Chebyshev==========================");

        DistanceMatrix distanceMatrixChebyshev = new DistanceMatrix(dataSet, DistanceUtils::distChebyshev, false);
        System.out.println(distanceMatrixChebyshev);

        System.out.println("==============Get distance ================================");
        System.out.println("Distanta dintre 1 si 0 este:  "+ distanceMatrixChebyshev.getDistance(1, 0));

    }
}
