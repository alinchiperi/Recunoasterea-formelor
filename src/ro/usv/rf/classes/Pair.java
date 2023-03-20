package ro.usv.rf.classes;

public class Pair <Q, P> {

    private Q weight;
    private P iClass;

    public Pair(Q weight, P iClass) {
        this.weight = weight;
        this.iClass = iClass;
    }

    public Q getWeight() {
        return weight;
    }

    public void setWeight(Q weight) {
        this.weight = weight;
    }

    public P getiClass() {
        return iClass;
    }

    public void setiClass(P iClass) {
        this.iClass = iClass;
    }
}
