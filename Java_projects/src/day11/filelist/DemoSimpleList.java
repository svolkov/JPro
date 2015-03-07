package day11.filelist;

//import java.awt.*;

import java.util.List;

/**
 * Created by syv on 02.03.15.
 */
public class DemoSimpleList {

    public static void main(String[] args){

//        SimpleList simpleList = new SimpleList();
//
//        simpleList.add("aaa");
//        simpleList.add("bbb");
//        simpleList.add("ccc");
//        simpleList.display();
//
//        simpleList.remove(2);
//        simpleList.display();
//
//       simpleList.add("ccc");
//        simpleList.add("ddd");
//        simpleList.add("ccc");
//        simpleList.display();

  /*      simpleList.remove(2);
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
        simpleList.display();*/

//        simpleList.close();

        SimpleFileList simpleFileList = new SimpleFileList("/home/syv/mydir/sflist.bin");


        simpleFileList.add("aaa");
        simpleFileList.add("bbbbbbbb");
        simpleFileList.add("ccccc");
        simpleFileList.add("ddd");
        simpleFileList.display();

//        simpleFileList.remove(0);
//        simpleFileList.display();

        List<String> list = simpleFileList.getAll();

        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));

        simpleFileList.close();

    }
}
