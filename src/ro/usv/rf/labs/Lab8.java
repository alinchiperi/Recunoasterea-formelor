package ro.usv.rf.labs;

import java.util.Arrays;

import ro.usv.rf.clustering.KMeansClustering;
import ro.usv.rf.learningsets.UnsupervisedLearningSet;

public class Lab8 {
	public static void main(String args[])
	{
		//todo: add needed code for lab8 problems
		
		UnsupervisedLearningSet unsupervisedSet = new UnsupervisedLearningSet("kmeans_example.txt");
		KMeansClustering kmeansClustering = new KMeansClustering(2, false);
		kmeansClustering.train(unsupervisedSet);
		System.out.println(Arrays.toString(kmeansClustering.getIClass()));
		
		
	}
}
