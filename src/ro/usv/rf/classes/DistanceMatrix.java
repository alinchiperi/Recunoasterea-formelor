package ro.usv.rf.classes;

import ro.usv.rf.utils.DistanceUtils;

import java.text.DecimalFormat;
import java.util.Arrays;

public class DistanceMatrix {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private double[][] matDist;

    public DistanceMatrix(double[][] patternSet) {
        int n = patternSet.length;
        matDist = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matDist[i][j] = DistanceUtils.distEuclid(patternSet[i], patternSet[j]);
            }
        }
    }

    public double[][] neighbors(int i) {
        double[][] neighbors = new double[2][matDist.length];
        for (int j = 0; j < matDist.length; j++) {
            neighbors[0][j] = j;
            neighbors[1][j] = matDist[i][j];
        }
        for (int j = 0; j < matDist.length; j++) {
            for (int k = j + 1; k < matDist.length; k++) {
                if (neighbors[1][j] > neighbors[1][k]) {
                    double aux = neighbors[1][j];
                    neighbors[1][j] = neighbors[1][k];
                    neighbors[1][k] = aux;

                    aux = neighbors[0][j];
                    neighbors[0][j] = neighbors[0][k];
                    neighbors[0][k] = aux;
                }
            }
        }
        return neighbors;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (double[] lin : matDist) {
            for (double xcrt : lin)
                stringBuilder.append(df.format(xcrt)).append(" ");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
