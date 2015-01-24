package day10.generics;

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


    }
}
