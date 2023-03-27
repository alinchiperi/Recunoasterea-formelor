package ro.usv.rf.classifiers;

import ro.usv.rf.classes.IDistance;
import ro.usv.rf.utils.DistanceUtils;

import java.util.Arrays;
import java.util.Random;

public class Classifier_1NN extends AbstractClassifier {

    IDistance d;
    private boolean debug = true;

    public Classifier_1NN() {
        d = DistanceUtils::distEuclid;
    }

    @Override
    public void training() {
        if (M == 0)
            throw new RuntimeException("train(): No supervised learning set provided (M=0)");
        // all rest were done in super.train(X,F,iClass)
    }

    @Override
    public int predict(double[] z) {
        int[] ind = new int[]{-1, -1};   // ind[0]=index of current pattern
        //  ind[1]=index of pattern situated at minimum distance
        double dmin = Arrays.stream(X)
                .mapToDouble(x -> d.calcDist(x, z))
                .reduce(Double.MAX_VALUE,
                        (dprec, dcrt) -> {
                            ind[0]++;
                            if (dcrt <= dprec) {
                                ind[1] = ind[0];
                                return dcrt;
                            }
                            return dprec;
                        });
        if (ind[1] < 0 || ind[1] >= n)
            System.out.println("X=" + Arrays.deepToString(X) + " n=" + n + "p=" + p + " ind=" + Arrays.toString(ind) + " z=" + Arrays.toString(z));
        return iClass[ind[1]];

    }

    private int findClosestPatternIndex(double[] z) {
        int closestPatternIndex = -1;
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            double distance = d.calcDist(X[i], z);
            if (distance < minDistance) {
                minDistance = distance;
                closestPatternIndex = i;
            }
        }
        return closestPatternIndex;
    }


    static public void classifyAndDisplayResult(AbstractClassifier classifier, String[] classNames, double[][] testSet) {
        System.out.println("\nPatterns class:" + Arrays.deepToString(testSet) + ":");
        Arrays.stream(classifier.predict(testSet))
                .mapToObj(k -> (classNames == null ? k : classNames[k]) + " ")
                .forEach(System.out::print);
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }


}
