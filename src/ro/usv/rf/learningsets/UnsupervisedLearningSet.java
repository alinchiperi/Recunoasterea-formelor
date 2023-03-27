package ro.usv.rf.learningsets;

import ro.usv.rf.classes.Pattern;
import ro.usv.rf.utils.FileUtils1;
import ro.usv.rf.utils.StatisticsUtils;

import java.util.Arrays;
import java.util.Map;


public class UnsupervisedLearningSet {
    protected double[][] X;  // the pattern matrix
    protected double[] f;  // the weight of each pattern
    protected int n;         // number of patterns
    protected int p;         // number of features
    protected int M;         // number of classes (=0 for unsupervised set)

    //
    public UnsupervisedLearningSet(String dataFileName) {
        X = FileUtils1.readMatrixFromFileStream(dataFileName);
        fillFieldValues(X, null);
    }

    public UnsupervisedLearningSet(double[][] X) {
        fillFieldValues(X, null);
    }

    public UnsupervisedLearningSet(double[][] X, double[] f) {
        fillFieldValues(X, f);
    }

    protected UnsupervisedLearningSet() {
    }

    protected void fillFieldValues(double[][] X, double[] f) {
        this.X = X;
        n = X == null ? 0 : X.length;
        p = X == null || X[0] == null ? 0 : X[0].length;

        for (int i = 1; i < n; i++) {
            if (X[i].length != p) {
                throw new IllegalArgumentException("All patterns must have the same number of features");
            }
        }
        if (this instanceof SupervisedLearningSet)
            return;
        if (f == null) {
            f = calculateWeightsValues();
        }
        this.f = f;
    }

    public double[] calculateWeightsValues() {
        double[] f = new double[X.length];
        Map<Pattern, Double> patternsMap = StatisticsUtils.getPatternsMapFromInitialSet(X);
        int i = 0;
        for (Map.Entry<Pattern, Double> entry : patternsMap.entrySet()) {
            f[i++] = entry.getValue();
        }
        n = patternsMap.size();

        return f;
    }

    public int getN() {
        return n;
    }

    public int getP() {
        return p;
    }

    public int getM() {
        return M;
    }

    public double[][] getX() {
        return X;
    }

    public double[] getF() {
        return f;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append(": n=").append(n).append(", p=").append(p).append(", M=").append(M);
        sb.append("\nNr.crt. ,  X ,  [f]:\n");
        for (int i = 0; i < n; i++) {
            sb.append(String.format("%d.  ", i + 1));
            for (double elemCrt : X[i])
                sb.append(String.format("%5.2f  ", elemCrt));
            sb.append(String.format("   [%5.2f]", f[i])).append("\n");
        }
        return sb.toString();
    }


}
