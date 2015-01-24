package day10.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by syv on 19.01.15.
 */
public class MyBox <M extends Tool> {
    private M item;
    private List <M> container;

    public MyBox(){
        container = new ArrayList();
    }

    public void addItem(M item) {
        container.add(item);
    }

    public M getItem() {
       if(!container.isEmpty()) {
           return container.get(container.size()-1);
       }
       return null;
    }

    public void removeItem(){
        if(!container.isEmpty()) {
            container.remove(container.size() - 1);
        }
    }

    public void sortBox(){
        Collections.sort(container);
    }

    public void display(){
        if(container.isEmpty()) {
            System.out.println("Box is empty!");
        }else {
            for(int i=0; i<container.size();i++) {
              System.out.println(container.get(i).getName());
            }
            System.out.println();
        }
        
    }
}
