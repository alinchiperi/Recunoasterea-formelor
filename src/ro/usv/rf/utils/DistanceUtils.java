package ro.usv.rf.utils;

import ro.usv.rf.exceptions.DifferentDimensionsException;

public class DistanceUtils {
    public static double distEuclid(double[] x, double[] y) {
        int p = x.length;
        if (p != y.length) throw new DifferentDimensionsException(p, y.length);
        double dist = 0.;
        for (int j = 0; j < p; j++) {
            dist += (x[j] - y[j]) * (x[j] - y[j]);
        }
        return Math.sqrt(dist);
    }

    public static double distManhattan(double[] x, double[] y) {
        int p = x.length;
        if (p != y.length) throw new DifferentDimensionsException(p, y.length);
        double dist = 0.;
        for (int j = 0; j < p; j++) {
            dist += Math.abs(x[j] - y[j]);
        }
        return dist;
    }

    public static double distChebyshev(double[] x, double[] y) {
        int p = x.length;
        if (p != y.length) throw new DifferentDimensionsException(p, y.length);
        double dmax = 0;
        for (int j = 0; j < p; j++) {
            dmax = Math.max(dmax, Math.abs(x[j] - y[j]));
        }
        return dmax;
    }

}
