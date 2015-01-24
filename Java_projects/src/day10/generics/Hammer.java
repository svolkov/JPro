package day10.generics;

/**
 * Created by syv on 19.01.15.
 */
public class Hammer extends Tool {
    private double weight;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Object o) {
        return ((Tool) o).getName().compareTo(this.getName());
    }
}
