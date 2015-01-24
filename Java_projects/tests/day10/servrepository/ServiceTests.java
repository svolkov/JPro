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
public class ServiceTests {
    Service service;

    @Before
    public void init(){
        service = new Service();
    }

    @Test
    public void checkServiceNameDefaultValue(){
        Assert.assertNull("Default Service's name should be null", service.getName());
    }
    @Test
    public void checkSetServiceName(){
        String name = "Serv1";
        service.setName(name);
        Assert.assertEquals(name,service.getName());
    }
}
