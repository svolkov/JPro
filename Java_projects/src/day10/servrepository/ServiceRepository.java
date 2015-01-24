package day10.servrepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syv on 21.01.15.
 */
public class ServiceRepository <M extends Service>{
    private List<M> container;

    public ServiceRepository(){
        container = new ArrayList<>();
    }

    public void add(M item) {
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
}
