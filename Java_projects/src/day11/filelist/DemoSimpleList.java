package day11.filelist;

/**
 * Created by syv on 02.03.15.
 */
public class DemoSimpleList {

    public static void main(String[] args){

        SimpleList simpleList = new SimpleList();
        simpleList.init();
        simpleList.add("aaa");
        simpleList.add("bbb");
        simpleList.add("ccc");
        simpleList.display();
        simpleList.remove(2);
        simpleList.display();
        simpleList.add("ccc");
        simpleList.add("ddd");
        simpleList.display();
        simpleList.remove(2);
        simpleList.display();
        simpleList.remove(1);
        simpleList.display();
        simpleList.remove(0);
        simpleList.display();
        simpleList.remove(0);
        simpleList.display();
        simpleList.add("aaa");
        simpleList.add("bbb");
        simpleList.add("ccc");
        simpleList.display();
        simpleList.close();
    }
}
