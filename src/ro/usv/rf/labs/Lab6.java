package ro.usv.rf.labs;

import ro.usv.rf.classes.Pattern;
import ro.usv.rf.classifiers.Classifier_1NN;
import ro.usv.rf.classifiers.Classifier_KNN;
import ro.usv.rf.graphic.DrawingPanel;
import ro.usv.rf.graphic.PatternImage;
import ro.usv.rf.learningsets.SupervisedLearningSet;
import ro.usv.rf.mnist.MNIST_FileUtil;
import ro.usv.rf.mnist.MNIST_SetUtils;
import ro.usv.rf.utils.DistanceUtils;

import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.BiFunction;

import static ro.usv.rf.mnist.MNIST_SetUtils.afisDurataExecutie;
import static ro.usv.rf.mnist.MNIST_SetUtils.convert_MNIST_SupervisedSet;

public class Lab6 {


    public static void main(String[] args) {
//        String filePrefixTest = "mnist\\t10k";
//        String filePrefixTrain = "mnist\\train";
//
//        int k = 3;
//        System.out.println("START");
//        String[] classNames = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
//        int check = 16000;
//        Object tab[] = MNIST_FileUtil.loadImageSet(filePrefixTrain, 100 + check);
//        double[][] X = (double[][]) tab[0];
//        int[] icl = (int[]) tab[1];
//        double[][] first100X = Arrays.copyOfRange(X, 0, 100);
//        int[] first100iClass = Arrays.copyOfRange(icl, 0, 100);
//
//        double[][] lastX = Arrays.copyOfRange(X, 100, 100 + check);
//        int[] lastiClass = Arrays.copyOfRange(icl, 100, 100 + check);
//
//        System.out.println("\n\n --------------- K = " + k + " -----------------");
//        Classifier_KNN cls = new Classifier_KNN(k);
//
//        SupervisedLearningSet setSuperv5 = new SupervisedLearningSet(lastX, lastiClass);
//        SupervisedLearningSet testSetZ15 = new SupervisedLearningSet(first100X, first100iClass);
//
//        testSetZ15.doSameClassIndexAs(setSuperv5);
//
//        Classifier_KNN knnClassifier = new Classifier_KNN(k);
//
//        knnClassifier.train(setSuperv5);
//
//
//        knnClassifier.evaluateAccuracy(testSetZ15, true);


//		MNIST_SetUtils.demo(600, 100, MNIST_SetUtils::printErrors);
//
//		PatternImage.demo("t10k", 30);  // the name of MNIST set: train or t10k
//		                               // 30 patterns will be drawn


        String filePrefixTrain = "mnist\\train";
        String filePrefixTest = "mnist\\t10k";

        int nbPatternsInSet = 10000;
        System.out.println("Start");
        SupervisedLearningSet trainingSet = convert_MNIST_SupervisedSet(filePrefixTrain, nbPatternsInSet);
        SupervisedLearningSet testSet = convert_MNIST_SupervisedSet(filePrefixTest, nbPatternsInSet);

//        Scanner kb = new Scanner(System.in);
//        while (true) {
//            System.out.print("k (pt stop-> 0)=");
//            int k = kb.nextInt();
//            if (k == 0) break;
//            Classifier_KNN cls = new Classifier_KNN(k);
//            cls.train(trainingSet);
//
//            System.out.print("Pattern index [0," + (testSet.getN() - 1) + "]=");
//            int patternIndex = kb.nextInt();
//            int patternClass = cls.predict(testSet.getX()[patternIndex]);
//            Pattern neighbors[] = cls.getPaternNeighbors();
//            double xNeighbors[][] = Arrays.stream(neighbors).map(Pattern::getPatternValues).toArray(double[][]::new);
//            int iClassNeighbors[] = Arrays.stream(neighbors)
//                    .mapToInt(Pattern::getIclass)
//                    .toArray();
//
//
//            DrawingPanel panel = new DrawingPanel(1200, 1600);
//            PatternImage drawImgs = new PatternImage(panel, 28, 28, 5);
//            int gap = 48;
//            int width = 28 * 5 + 2 + gap;
//            drawImgs.drawPatternImage(testSet.getX()[patternIndex], gap / 2, gap / 3, "Test_" + patternIndex
//                            + " is in class " + trainingSet.getClassNames()[patternClass]
//                    , (patternClass == testSet.getIClass()[patternIndex] ? Color.black : Color.MAGENTA));
//            int y1 = gap / 3 + width + 30;
//            Graphics g = drawImgs.getG();
//            g.setColor(Color.BLUE);
//            g.drawString("The first " + k + " neighbours of this pattern in " + filePrefixTrain, gap / 2, y1);
//            g.setColor(Color.BLACK);
//            g.drawLine(gap / 2, y1 + 5, gap / 2 + 6 * width - gap, y1 + 5);
//
//            drawImgs.drawImageArray(xNeighbors, iClassNeighbors, trainingSet.getClassNames(), k, gap / 2, gap / 3 + width + 50, gap, null);
//        }
        MNIST_SetUtils.demo(600, 100, MNIST_SetUtils::printErrors);
        MNIST_SetUtils.demo(600, 100, MNIST_SetUtils::displayErrors);
//        MNIST_SetUtils.displayErrors(600, 100, MNIST_SetUtils::printErrors);

    }


}