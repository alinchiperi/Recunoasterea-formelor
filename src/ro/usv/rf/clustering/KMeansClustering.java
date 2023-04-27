package ro.usv.rf.clustering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ro.usv.rf.classes.IDistance;
import ro.usv.rf.classes.Pair;
import ro.usv.rf.utils.DistanceUtils;


public class KMeansClustering extends AbstractClustering {

    private boolean useRandomStartCentroids;
    private double[][] c; // centroids array
    private IDistance d;

    public KMeansClustering(int k) {
        this(k, false, DistanceUtils::distEuclid);
    }

    public KMeansClustering(int k, boolean randomC) {
        this(k, randomC, DistanceUtils::distEuclid);
    }

    public KMeansClustering(int k, boolean randomC, IDistance d) {
        this.M = k;
        this.useRandomStartCentroids = randomC;
        this.d = d;
    }

    @Override
    public void training() {

        this.c = new double[M][p];
        //patterns orders list
        List<Integer> numberList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            numberList.add(i);
        }
        //if random is true shuffle the patterns orders list
        if (useRandomStartCentroids) {
            Collections.shuffle(numberList);
        }
        for (int i = 0; i < this.M; i++) {
            double[] selectedCentroid = X[numberList.get(i)];
            c[i] = Arrays.copyOf(selectedCentroid, selectedCentroid.length);
        }


    }

    public int[] getIClass()
    {
        int[] iClass = new int[n];
        boolean done = false;

        int kMin = 0;

        for (int i = 0; i < M; i++)
        {
            iClass[i] = i+1;
        }
        double[] miu = new double[M];
        double[][] g = new double[M][p];
        while (!done) {
            done = true;


            for (int i = 0; i < M; i++)
            {
                for (int j = 0; j < p; j++)
                {
                    g[i][j] = 0;
                }
                miu[i] = 0;
            }

            for (int i = 0; i < n; i++) {
                double dMin = Double.MAX_VALUE;

                for (int k = 0; k < M;k++) {
                    double disCurrent = d.calculateDistance(X[i], c[k]);

                    if (disCurrent < dMin) {
                        dMin = disCurrent;
                        kMin = k;
                    }
                }

                miu[kMin] += f[i];
                for (int j = 0; j < p; j++) {
                    g[kMin][j] += X[i][j] * f[i];
                }

                if (iClass[i] != (kMin + 1)) {
                    iClass[i] = kMin + 1;
                    done = false;
                }
            }

            if (!done) {
                for (int k = 0; k < M; k++) {
                    for (int j = 0; j < p; j++) {
                        g[k][j] /= miu[k];
                    }

                    c[k] = Arrays.copyOf(g[k], g[k].length);

                }
            }
        }
        System.out.println();
        return iClass;
    }

    @Override
    public String toString() {
        return "KMeansClustering [K=" + M + "]";
    }


}
