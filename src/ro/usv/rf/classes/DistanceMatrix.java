package ro.usv.rf.classes;

import ro.usv.rf.utils.DistanceUtils;

import java.text.DecimalFormat;

public class DistanceMatrix {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private double[][] matDist;
    private double[][] patternSet;
    private boolean full;
    private IDistance distance;


    public DistanceMatrix(double[][] patternSet) {
        int n = patternSet.length;
        matDist = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matDist[i][j] = DistanceUtils.distEuclid(patternSet[i], patternSet[j]);
            }
        }
    }

    public DistanceMatrix(double[][] patternSet, IDistance distance, boolean full) {
        this.patternSet = patternSet;
        this.distance = distance;
        this.full = full;
        if (full)
            calculateDistanceFullMatrix();
        else
            calculateDistanceTriangleMatrix();
    }

    public void setDistance(IDistance distance) {
        this.distance = distance;
        if (this.full)
            calculateDistanceFullMatrix();
        else
            calculateDistanceTriangleMatrix();

    }

    public void setFull(boolean full) {
        this.full = full;
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


    private void calculateDistanceFullMatrix() {
        int n = patternSet.length;
        matDist = new double[n][n];
        for (int i = 0; i < n; i++) {
            matDist[i][i] = 0;
            for (int j = 0; j < i; j++) {
                matDist[i][j] = matDist[j][i] = this.distance.calcDist(patternSet[i], patternSet[j]);
            }
        }
    }

    private void calculateDistanceTriangleMatrix() {
        int n = patternSet.length;
        matDist = new double[n - 1][];
        for (int i = 1; i < n; i++) {
            matDist[i - 1] = new double[i];
            for (int j = 0; j < i; j++) {
                matDist[i - 1][j] = this.distance.calcDist(patternSet[i], patternSet[j]);
            }
        }
    }

    public double getDistance(int i, int j) {
        if (this.full)
            return matDist[i][j];
        return i == j ? 0. : (i > j ? matDist[i][j] : matDist[j][i]);
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
