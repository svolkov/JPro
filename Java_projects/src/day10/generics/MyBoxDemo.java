package day10.generics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by syv on 19.01.15.
 */
public class MyBoxDemo {
    public static void main(String[] args){
        //System.out.println("My Box");

        MyBox<Hammer> box1 = new MyBox();
        MyBox<Screwdriver> box2 = new MyBox();
        MyBox<Tool> box3 = new MyBox();

        Hammer hammer = new Hammer();
        hammer.setName("Super Hammer");
        Screwdriver screwdriver = new Screwdriver();
        screwdriver.setName("Clever Screwdriver");

        box1.addItem(hammer);
        box2.addItem(screwdriver);
        box3.addItem(hammer);

        Hammer hammer1 = new Hammer();
        hammer1.setName("Big Hammer");
        box1.addItem(hammer1);
        Hammer hammer2 = new Hammer();
        hammer2.setName("Huge Hammer");
        box1.addItem(hammer2);

        box1.sortBox();
        box1.display();

        box3.addItem(screwdriver);
        box3.addItem(hammer2);
        box3.addItem(hammer2);
        box3.addItem(hammer1);
        box3.addItem(hammer);

        box3.sortBox();
        box3.display();

        List<Hammer> hammerList = new ArrayList<>();
        List<Tool> toolList = new ArrayList<>();
        List<Tool> toolList1 = new ArrayList<>();
        List<Screwdriver> screwdriverList = new ArrayList<>();

        hammerList.add(hammer);
        hammerList.add(hammer);
        hammerList.add(hammer1);
        hammerList.add(hammer1);
        hammerList.add(hammer2);
        hammerList.add(hammer2);

        box1.copy(hammerList,toolList);
        System.out.println(toolList);

        box3.copy(toolList,toolList1);
        System.out.println(toolList1);

    }
}
