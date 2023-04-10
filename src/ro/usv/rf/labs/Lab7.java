package ro.usv.rf.labs;

import ro.usv.rf.classifiers.AbstractClassifier;
import ro.usv.rf.classifiers.ClassClassifier;
import ro.usv.rf.classifiers.Classifier_KNN;
import ro.usv.rf.learningsets.SupervisedLearningSet;
import ro.usv.rf.utils.DataUtils;
import ro.usv.rf.utils.FileUtils1;
import ro.usv.rf.utils.SetUtils;

import java.util.Arrays;

public class Lab7 {
    public static void clasificaSiEvalueaza(AbstractClassifier cls, SupervisedLearningSet trainSet, SupervisedLearningSet evalSet) {
        cls.train(trainSet);
        if (cls.getW() != null) {
            System.out.println("Matricea coeficientilor functiilor discriminant");
            DataUtils.printMatrix(cls.getW());
        }

        System.out.println("Accuracy on training set is " + cls.evaluateAccuracy(trainSet, false) * 100 + "%");
//        System.out.println("Evaluation set" + evalSet);
        System.out.println("Accuracy on test set is " + cls.evaluateAccuracy(evalSet, false) * 100 + "%");
    }

    public static void main(String[] args) {
//        SupervisedLearningSet exam = new SupervisedLearningSet("testexam_strings.txt");
//        SupervisedLearningSet eval = new SupervisedLearningSet("test_z1_z5.txt");
//        eval.doSameClassIndexAs(exam);
//        System.out.println("evaluation set\n"+exam);

//        ClassClassifier cls = new ClassClassifier();
//
//        clasificaSiEvalueaza(cls, exam, eval);
//        cls.calculateSeparationSurfaces();
//        System.out.println("matricea coef. suprafetelor de separatie interclase");
//        DataUtils.printMatrix(cls.getSeparationSurface());
//
//        for(int k=1; k<20; k+=2)
//        {
//            System.out.println(k+"-NN");
//            clasificaSiEvalueaza(new Classifier_KNN(k), exam, eval);
//        }

        System.out.println("===============Exercitiul 1 a  =====================");
        AbstractClassifier abstractClassifier = new ClassClassifier();
        SupervisedLearningSet exam = new SupervisedLearningSet("lab7.txt");
        abstractClassifier.train(exam);
        double[][] predicted = new double[][]{{1, 3}, {4, 5}, {0, 0}};
        System.out.println("Points: " + Arrays.deepToString(predicted));
        System.out.println("classes: " + Arrays.toString(abstractClassifier.predict(predicted)));

        System.out.println("===============Exercitiul 1 b  =====================");
        FileUtils1.setinputFileValuesSeparator(","); // by default is white spaces
        SupervisedLearningSet countyLearningSet = new SupervisedLearningSet("county_data.txt");

        abstractClassifier.train(countyLearningSet);


        int predictedCountry = abstractClassifier.predict(new double[]{24.20, 45.87});

        System.out.println(predictedCountry + " -> " + countyLearningSet.getClassNames()[predictedCountry]);


//        FileUtils1.setinputFileValuesSeparator(",");
//        SupervisedLearningSet irisSet = new SupervisedLearningSet("lab7_iris.csv");
//        //code for splitting set into training and test sets
//        SupervisedLearningSet[] sets = SetUtils.splitSet(irisSet, 15);
//        SupervisedLearningSet trainSet = sets[0];
//        SupervisedLearningSet testSet = sets[1];
//
//        clasificaSiEvalueaza(cls, trainSet, testSet);
//
//        for (int k = 1; k < 20; k++) {
//            System.out.println(k + "-nn");
//            clasificaSiEvalueaza(new Classifier_KNN(k), trainSet, testSet);
//        }
    }
}
