package day10.generics;

/**
 * Created by syv on 19.01.15.
 */
public class Screwdriver extends Tool {
    private double length;

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public int compareTo(Object o) {
        return ((Tool) o).getName().compareTo(this.getName());
    }
}
