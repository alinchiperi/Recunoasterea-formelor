package ro.usv.rf.exceptions;

public class DifferentDimensionsException extends RuntimeException{
    public DifferentDimensionsException(int d1, int d2) {
        super("d1=" + d1 +", d2=" + d2);
    }
}
