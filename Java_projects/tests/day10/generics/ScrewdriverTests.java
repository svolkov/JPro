package day10.generics;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by syv on 23.01.15.
 */
@RunWith(JUnit4.class)
public class ScrewdriverTests {
    Screwdriver screwdriver;
    @Before
    public void  init() {
        screwdriver = new Screwdriver();
        // hammer.setPrice(120);
        // hammer.setWeight(120.3);
    }
    @Test
    public void checkScrewdriverNameDefaultValue(){
        Assert.assertNull("Default screwdriver's name should be null", screwdriver.getName());
    }
    @Test
    public void checkScrewdriverPriceDefaultValue(){
        Assert.assertEquals("Default screwdriver's price should be 0",0,screwdriver.getPrice());
    }
    @Test
    public void checkScrewdriverLengthDefaultValue(){
        Assert.assertEquals("Default screwdriver's length should be 0",0,screwdriver.getLength(),0.001);
    }

    @Test
    public void checkSetScrewdriverPrice(){
        int price = 10;
        screwdriver.setPrice(price);
        Assert.assertEquals(price,screwdriver.getPrice());
    }
    @Test
    public void checkSetScrewdriverName(){
        String name = "Big Screwdriver";
        screwdriver.setName(name);
        Assert.assertEquals(name,screwdriver.getName());
    }
    @Test
    public void checkSetHammerLength(){
        double length = 9.5;
        screwdriver.setLength(length);
        Assert.assertEquals(length,screwdriver.getLength(),0.001);
    }
    @Test
    public void checkCompareToEqual(){
        Screwdriver screwdriver1 = new Screwdriver();
        screwdriver1.setName("Big Screwdriver");
        screwdriver.setName("Big Screwdriver");
        Assert.assertEquals(0, screwdriver.compareTo(screwdriver1));
    }
    @Test
    public void checkCompareToLess(){
        Screwdriver screwdriver2 = new Screwdriver();
        screwdriver2.setName("Angry Screwdriver");
        screwdriver.setName("Big Screwdriver");
        Assert.assertTrue(screwdriver.compareTo(screwdriver2) < 0);
    }
    @Test
    public void checkCompareToMore(){
        Screwdriver screwdriver3 = new Screwdriver();
        screwdriver3.setName("Small Screwdriver");
        screwdriver.setName("Big Screwdriver");
        Assert.assertTrue(screwdriver.compareTo(screwdriver3)>0);
    }
}
