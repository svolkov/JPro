package day10.servrepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by syv on 23.01.15.
 */
@RunWith(JUnit4.class)
public class ServiceRepositoryTests extends Assert {
   ServiceRepository servrepo;

@Before
    public void setup(){

     servrepo = new ServiceRepository();
    }

    @Test
    public void checkServRepoCreation(){
        assertNotNull("ServiceRepository has not been created",servrepo);
    }

    @Test
    public void checkServRepoAddItem(){
       Service service = new Service();
       servrepo.add(service);
       assertEquals(service, servrepo.getItem());
    }

    @Test
    public void checkServRepoGetItemIfEmpty(){
      assertNull(null, servrepo.getItem());
    }

    @Test
    public void checkServRepoRemoveItem(){
        Service service = new Service();
        servrepo.add(service);
        servrepo.removeItem();
        assertNull(null, servrepo.getItem());
    }
}
