package day11.filelist;

import java.util.List;

/**
 * Created by syv on 03.03.15.
 */
public interface SimpleListOnFile {
    public void add(String str);
    public void remove(int index);
    public void display();
    public List<String> getAll();
}
