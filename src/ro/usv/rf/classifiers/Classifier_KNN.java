package ro.usv.rf.classifiers;

import ro.usv.rf.classes.IDistance;
import ro.usv.rf.classes.Neighbour;
import ro.usv.rf.classes.Pattern;
import ro.usv.rf.utils.DistanceUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Classifier_KNN extends AbstractClassifier {

    IDistance d;

    Pattern[] patternNeighbors ;
    int k;
    private boolean debug = true;

    public Classifier_KNN(int k, IDistance d) {
        super();
        this.d = d;
        this.k = k;
    }

    public Classifier_KNN(int k) {
        this(k, DistanceUtils::distEuclid);
    }


    @Override
    public void training() {
        if (M == 0)
            throw new RuntimeException("train(): No supervised learning set provided (M=0)");
        // all rest were done in super.train(X,F,iClass)
    }

    @Override
    public int predict(double[] z) {
        List<Neighbour> neighbours = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (Arrays.equals(X[i], z)) {
                continue; // skip if the pattern is the same as z
            }
            double distance = d.calculateDistance(X[i], z);
            neighbours.add(new Neighbour(distance, i));
        }
        Collections.sort(neighbours);

        patternNeighbors = new Pattern[k];
        for (int i = 0; i < k; i++) {
            int index = neighbours.get(i).getIClass();
            patternNeighbors[i] = new Pattern(X[index], iClass[index]);
        }

        Map<Integer, Integer> classCounts = new HashMap<>();
        for (int i = 0; i < k; i++) {
            int classIndex = iClass[neighbours.get(i).getIClass()];
            int count = classCounts.getOrDefault(classIndex, 0);
            classCounts.put(classIndex, count + 1);
        }
        int maxCount = 0;
        int predictedClass = -1;
        for (Map.Entry<Integer, Integer> entry : classCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                predictedClass = entry.getKey();
            }
        }

        return predictedClass;

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


    public Pattern[] getPaternNeighbors() {


        return patternNeighbors;
    }
}
