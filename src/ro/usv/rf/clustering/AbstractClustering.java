package ro.usv.rf.clustering;

import java.util.Arrays;

import ro.usv.rf.learningsets.UnsupervisedLearningSet;

public abstract class AbstractClustering {

	protected int n; // number of patterns
	protected int p; // number of features
	protected int M; // number of classes
	protected double[][] X; // patterns matrix [n][p]
	protected double[] f; // patterns weights (f[i] = the weight of pattern i)


    abstract public void training();  
    
	private void train(double[][] X, double[] f, String[] classNames) {
		if (X == null)
			throw new RuntimeException("train(): NO data set provided");
		this.X = X;
		n = X.length; // number of patterns
		if (n == 0)
			throw new RuntimeException("train(): No patterns in data set (n=0)");
		p = X[0].length; // number of features (defined in Abstract Classifier)
		if (p == 0)
			throw new RuntimeException("train(): Patterns with no features in data set (p=0)");
		if (f == null) {
			f = new double[n];
			Arrays.fill(f, 1.);
		}
		this.f = f;
		training();   // to be overrided in sub-classes
	}

	public void train(UnsupervisedLearningSet unsupervisedSet) {
		train(unsupervisedSet.getX(), unsupervisedSet.getF(),  null);
	}

}
