package ro.usv.rf.learningsets;

import ro.usv.rf.classes.Pair;
import ro.usv.rf.classes.Pattern;
import ro.usv.rf.utils.StatisticsUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SupervisedLearningSet extends UnsupervisedLearningSet {

    // X[][], f[], n, p are defined in super class
    private int[] iClass;         // the label (index) class of each pattern  
    private String[] classNames;  // an array of M+1 elements containing the names of the classes
    // because iClass[i] is in [1, M] (iClass[i]=0 means unknown class)
    // classNames[0]="" (reserved, not used),
    // classNames[1]= the name of the first class,
    // classNames[2]= the name of the second class, ...
    // classNames[M] = the name of the M-th class

    public SupervisedLearningSet(double[][] X, int[] iClass) {
        fillSupervisedFieldsValues(X, iClass, null);
    }

    // This constructor is designed for cases where the user
    // has used numbers to indicate class labels and the last 
    // column of X (the matrix returned by the UnsupervisedLearningSet constructor)
    // contains the class labels for each pattern
    // Optionally may be indicated the classes names or null
    //
    public SupervisedLearningSet(String dataFileName, String[] classNames) {
        super(dataFileName);
        // extract the numerical class labels from X
        double[][] newX = new double[n][];
        iClass = new int[n];
        for (int i = 0; i < n; i++) {
            newX[i] = Arrays.copyOf(X[i], p - 1);  //without class label
            iClass[i] = (int) X[i][p - 1];
        }
        fillSupervisedFieldsValues(newX, iClass, classNames);
    }

    public double[] calculateWeightsValues() {
        double[] f = new double[X.length];

        int kl = 0;
        Map<Pattern, Pair<Double, Integer>> patternsMap = StatisticsUtils.getPatternsMapFromInitialSet(X, iClass);
        n = patternsMap.size();
        X = new double[n][p];
        iClass = new int[n];
        for (Map.Entry<Pattern, Pair<Double, Integer>> entry : patternsMap.entrySet())
        {
            Pattern patternObject = entry.getKey();

            for (int j = 0; j < p; j++) {
                X[kl][j]= patternObject.getPatternValues()[j];
            }
            f[kl] = entry.getValue().getWeight();
            iClass[kl] = entry.getValue().getiClass();
            kl++;

        }

        // check if 2 identical patterns has same class (must be!)
        // if a reduced set is produce update the value of n
        return f;
    }

    private void fillSupervisedFieldsValues(double[][] X, int[] iClass, String[] classNames) {
        this.X = X;
        n = X == null ? 0 : X.length;
        p = X == null || X[0] == null ? 0 : X[0].length;
        // TODO check if all the patterns have the same number of features
        //            if not throw an Exception
        //       Use the method you will write in super class.
        if (iClass != null) {
            this.iClass = iClass;
            this.classNames = classNames != null ? classNames : obtainClassNames(iClass);
            M = this.classNames.length - 1;  // because classNames[0]="" (reserved, not used)
        } else {
            iClass = new int[n];
            Arrays.fill(iClass, 0);
            M = 0;    // no classes defined; in fact is not a supervised set
        }
        this.f = calculateWeightsValues();
    }

    public static String[] obtainClassNames(int[] iClass) {
        List<String> lstClassNames = Arrays.stream(iClass)
                .distinct()
                .mapToObj(String::valueOf)   // conversion from int number to String
                .collect(Collectors.toList());
        lstClassNames.add("");   // added for obtaining directly the class names directly as
        //     "classNames [ iClass[i] ]", where iClass[i] is in [1, M]
        // (not with "iClass[i]-1")
        String[] classNames = new String[lstClassNames.size()];   //M+1 because is already added a ""
        lstClassNames.toArray(classNames);
        Arrays.sort(classNames);
        return classNames;
    }


    public int[] getIClass() {
        return iClass;
    }

    public String[] getClassNames() {
        return classNames;
    }

    @Override
    public String toString() {
        String[] linii = super.toString().split("\n");
        StringBuilder sBuilder = new StringBuilder(linii[0]).append("\n");
        for (int i = 0; i < n; i++)
            sBuilder.append(linii[i + 2])
                    .append(String.format("   <%s>", classNames[iClass[i]])).append("\n");
        return sBuilder.toString();
    }


}
