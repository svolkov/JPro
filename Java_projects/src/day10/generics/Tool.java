package day10.generics;

/**
 * Created by syv on 19.01.15.
 */
public abstract class Tool implements Comparable {
    private String name;
    private int price;

    public Tool() {
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

}
