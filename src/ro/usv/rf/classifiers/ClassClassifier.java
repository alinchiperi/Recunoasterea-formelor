/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.usv.rf.classifiers;

public class ClassClassifier extends AbstractClassifier {

    private double[][] separationSurfaces;

    @Override
    public void training() {
        w = new double[M][p + 1];
        double[] miu = new double[M];
        for (int i = 0; i < n; i++) {
            int k = iClass[i] - 1;
            miu[k] += f[i];
            for (int j = 0; j < p; j++) {
                w[k][j] += X[i][j] * f[i];
            }
        }

        for (int k = 0; k < M; k++) {
            for (int j = 0; j < p; j++) {
                w[k][j] /= miu[k];
                w[k][p] -= w[k][j] * w[k][j] / 2;
            }
        }
    }

    // int predict(doble x) may be already defined in AbstractClassifier
    //
    // Returns the predicted index of the class 
    // to which the pattern x might belong.
    // Predict only for linear models of classifiers
    //
    @Override
    public int predict(double[] z) {
        if (w == null) {
            throw new RuntimeException("Trebuie executat mai intai train()");
        }
        int imax = 0;
        double psik;
        double psimax = Double.NEGATIVE_INFINITY;
        for (int k = 0; k < M; k++) {
            psik = w[k][p];
            for (int j = 0; j < p; j++) {
                psik += w[k][j] * z[j];
            }
            if (psik > psimax) {
                psimax = psik;
                imax = k;
            }
        }
        return imax + 1;
    }

    public void calculateSeparationSurfaces() {
        if (w == null)
            throw new RuntimeException("Trebuie executat mai intai train");
        //s.sep:0-1,...,0-(M-1) = M-1 ecuatii
        //1-2,...,1-(M-1) = M-2 ecuatii
        //2-3,...,2-(M-1),... = M-3 Ecuatii
        // ...,
        // (M-2)-(M-1) = 1 ecuatii
        //in total avem (M-1)+...+2+1=M*(M+1)/2
        separationSurfaces = new double[M * (M - 1) / 2][p + 1];
        int ind = -1;
        for (int i = 0; i < M - 1; i++) {
            for (int j = i + 1; j < M; j++) {
                ind++;
                for (int k = 0; k < p + 1; k++) {
                    separationSurfaces[ind][k] = w[i][k] - w[j][k];
                }
            }
        }
    }

    public double[][] getSeparationSurface() {
        if (separationSurfaces == null)
            throw new RuntimeException("suprafetele nu au fost calculate");
        return separationSurfaces;
    }


}
