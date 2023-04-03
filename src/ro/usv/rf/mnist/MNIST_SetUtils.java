package ro.usv.rf.mnist;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import ro.usv.rf.classifiers.Classifier_1NN;
import ro.usv.rf.graphic.DrawingPanel;
import ro.usv.rf.graphic.PatternImage;
import ro.usv.rf.learningsets.SupervisedLearningSet;
import ro.usv.rf.utils.DistanceUtils;

public class MNIST_SetUtils {

    public static double[][] convertToMatrixListOfDigitData(List<DigitData> lstDigitData,
                                                            int[] iClass, int nMax) {
        final int ind[] = new int[]{-1};
        return lstDigitData.stream()  // Stream<DigitData>
                .limit(nMax)
                .map(digObj -> {
                    iClass[++ind[0]] = digObj.getLabel() + 1;
                    return digObj.getData();
                })   // Stream<double[]>
                .toArray(double[][]::new);
    }


    public static SupervisedLearningSet convertMNIST_SupervisedSet(String filePrefix, int nMax) {
        String[] classNames = {"", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		/*
		String[] classNames = IntStream.range(0, 11)  //->int
				.mapToObj(i-> i==0?"":""+(i-1))  //Stream<String>
				.toArray(String[]::new);
		*/
        List<DigitData> inageSet = MNIST_FileUtil.loadImageData(filePrefix);
        if (nMax <= 0)
            nMax = inageSet.size();

        int[] iClass = new int[nMax];
        System.out.println("Start conversion to double[][]");
        double[][] Ximag = convertToMatrixListOfDigitData(inageSet, iClass, nMax);
        System.out.println("Start conversion to SupervisedLearningSet");
        return new SupervisedLearningSet(Ximag, iClass, classNames);
    }

    public static SupervisedLearningSet convert_MNIST_SupervisedSet(String filePrefix, int nMax) {
        String[] classNames = {"", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        Object tab[] = MNIST_FileUtil.loadImageSet(filePrefix, nMax);
        int[] iClass = (int[]) tab[1];
        IntStream.range(0, iClass.length).forEach(i -> iClass[i]++);
        return new SupervisedLearningSet((double[][]) tab[0], iClass, classNames);
    }

    public static void afisDurataExecutie(long startTime, int N) {
        long duration = System.currentTimeMillis() - startTime;
        System.out.printf("Exec time %.3f s\nExec time  per pattern"
                        + " %.6f ms\n\n",
                (float) (duration / 1000.), (float) (duration / (double) N));
    }

    public static Void printErrors(SupervisedLearningSet set, int[] iClassCalculated) {
        printErrors(set, iClassCalculated, 0);
        return null;
    }

    public static void printErrors(SupervisedLearningSet set, int[] iClassCalculated, int nrMax) {
        int nPatterns = set.getIClass().length;
        int nr = 0;
        if (nrMax < 1)
            nrMax = Integer.MAX_VALUE;
        DigitData.printData = false;
        List<DigitData> printList = new ArrayList<DigitData>();
        for (int i = 0; i < nPatterns; i++) {
            if (set.getIClass()[i] != iClassCalculated[i]) {
                printList.add(new DigitData(set.getX()[i], set.getIClass()[i] - 1,
                        "Bad classification in class" + (iClassCalculated[i] - 1) + " "));
                System.out.println();
                if (++nr == 3) {  // display max 3 images on row
                    System.out.println(DigitData.toStringNImgNearby(printList, 0, nr - 1));
                    printList.clear();
                    nr = 0;
                }
                if (--nrMax <= 0)
                    break;
            }
        }
        if (nr > 0)
            System.out.println(DigitData.toStringNImgNearby(printList, 0, nr - 1));

    }

    public static Void displayErrors(SupervisedLearningSet set, int[] iClassCalculated) {
        int nPatterns = set.getIClass().length;
        DigitData.printData = false;
        DrawingPanel panel = new DrawingPanel(1200, 4000);
        PatternImage drawImgs = new PatternImage(panel, 28, 28, 5);
        int gap = 48;
        int width = 28 * 5 + 2 + gap;
        for (int i = 0; i < nPatterns; i++) {
            int correct = set.getIClass()[i] - 1;
            int predict = iClassCalculated[i] - 1;
            drawImgs.drawPatternImage(set.getX()[i], gap / 2 + (i % 5) * width,
                    gap / 3 + (i / 5) * width,
                    "Predicted: " + predict + ", Correct: " + correct ,
                    (set.getIClass()[i] == iClassCalculated[i] ? Color.BLUE : Color.MAGENTA));
        }
        return null;

    }

//    Accuracy of classification by 1NN the test set=92.25%
//    Nb. of correct classified patterns = 1845 of 2000

    public static void demo(int nbPatternsInTrainingSet, int nbPatternsInTestSet,
                            BiFunction<SupervisedLearningSet, int[], Void> display) {
        String filePrefixTest = "mnist\\t10k";
        String filePrefixTrain = "mnist\\train";
        System.out.println("Start");
        SupervisedLearningSet trainingSet = convert_MNIST_SupervisedSet(filePrefixTrain, nbPatternsInTrainingSet);
        Classifier_1NN cls = new Classifier_1NN(DistanceUtils::distEuclid);
        cls.train(trainingSet);
        SupervisedLearningSet testSet10k = convert_MNIST_SupervisedSet(filePrefixTest, nbPatternsInTestSet);
        System.out.printf("Training set with %d patterns%nTest set with %d patterns%n",
                nbPatternsInTrainingSet, nbPatternsInTestSet);

        long startTest = System.currentTimeMillis();
        double accuracy = cls.evaluateAccuracy(testSet10k, false);  //execute predict()
        afisDurataExecutie(startTest, testSet10k.getN());
        System.out.println("Accuracy of classification by 1NN the test set=" + accuracy * 100 + "%");
        System.out.println("Nb. of correct classified patterns = " + (int) (testSet10k.getN() * accuracy)
                + " of " + testSet10k.getN() + "\nClassification error =" + (1 - accuracy) * 100 + "%");
        display.apply(testSet10k, cls.getiClassCalculated());

    }

}

