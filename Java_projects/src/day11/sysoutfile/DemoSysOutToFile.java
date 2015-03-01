package day11.sysoutfile;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by syv on 15.02.15.
 */
public class DemoSysOutToFile {
    public  static void main(String[] args) throws FileNotFoundException {
        String pathFile = "/home/syv/mydir/printstring.txt";
        System.setOut(new MyOutputStream(new File(pathFile)));

        System.out.println("First String to file.");
        System.out.println("Second String to file.");

    }
}
