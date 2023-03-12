package ro.usv.rf.labs;

import ro.usv.rf.utils.DataUtils;
import ro.usv.rf.utils.FileUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lab1 {
    public static void main(String[] args) {
        System.out.println("Laborator 1");

        List<ArrayList<Double>> matrixAsLists= new ArrayList <>();
        ArrayList<Double> linie0 = new ArrayList<>(Arrays.asList(-1., 2.,-3.) );
        ArrayList<Double> linie1 = new ArrayList<>(Arrays.asList( 4.,-5., 6.) );

        matrixAsLists.add ( linie0 );
        matrixAsLists.add ( linie1 );

        double[][] matrix = FileUtils.convertToBiDimensionalArray(matrixAsLists);
        double[][] matrixFromFile = FileUtils.readLearningSetFromFile("matrix.txt");


        DataUtils.printMatrix(matrixFromFile);
        double[][] patternSet = DataUtils.normalizeLearningSet(matrixFromFile);

        FileUtils.writeLearningSetToFile("output.txt",patternSet);
        //Print matrix
        DataUtils.printMatrix(patternSet);


    }
}
