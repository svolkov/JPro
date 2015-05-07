package day13.shop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syv on 26.01.15.
 */
public class ShoppingCart {
    private List<Transaction> container;

    public ShoppingCart() {
        this.container = new ArrayList<>();
    }
    public void add(Transaction item) {
        container.add(item);
    }

    public Transaction getItem(int index) {
        if(!container.isEmpty()) {
            return container.get(index);
        }
        return null;
    }

    public void removeItem(){
        if(!container.isEmpty()) {
            container.remove(container.size() - 1);
        }
    }

    public void removeAll(){
        if(!container.isEmpty()) {
            container.removeAll(container);
        }
    }

    public double getSum(){
        double sum=0;
        if(!container.isEmpty()) {
            for (Transaction trans : container){
                sum+=trans.getPrice()*trans.getQuantity();
            }
        }
        return sum;
    }

    public int getDiscount(){
        double sum = 0;

        sum = getSum();
        if(sum >= 1000) {
            return 10;
        }else {
            if (sum >= 50)
            {
                return 5;
            } else {
                return 0;
            }
        }
    }

    public double getEndSum(){// Sum with discount

        return getSum() - getSum()*getDiscount()/100;
    }

    public int getSize(){
        return container.size();
    }
}
