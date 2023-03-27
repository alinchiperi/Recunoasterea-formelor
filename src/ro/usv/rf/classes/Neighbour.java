package ro.usv.rf.classes;

public class Neighbour implements Comparable<Neighbour> {

    private double distance;
    private int classIndex;
    @Override
    public int compareTo(Neighbour o)
    {
        return Double.compare(this.distance, o.distance);
    }

    public Neighbour( int classIndex, double distance) {
        this.distance = distance;
        this.classIndex = classIndex;
    }

    public Neighbour() {
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getClassIndex() {
        return classIndex;
    }

    public void setClassIndex(int classIndex) {
        this.classIndex = classIndex;
    }
}
