package day10.generics;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by syv on 22.01.15.
 */
@RunWith(JUnit4.class)
public class HammerTests {
    Hammer hammer;
@Before
    public void  init() {
        hammer = new Hammer();
       // hammer.setPrice(120);
       // hammer.setWeight(120.3);
    }
@Test
    public void checkHammerNameDefaultValue(){
      Assert.assertNull("Default hammer's name should be null",hammer.getName());
    }
 @Test
    public void checkHammerPriceDefaultValue(){
        Assert.assertEquals("Default hammer's price should be 0",0,hammer.getPrice());
    }
  @Test
    public void checkHammerWeightDefaultValue(){
        Assert.assertEquals("Default hammer's weight should be 0",0,hammer.getWeight(),0.001);
    }

  @Test
    public void checkSetHammerPrice(){
      int price = 10;
      hammer.setPrice(price);
      Assert.assertEquals(price,hammer.getPrice());
    }
    @Test
    public void checkSetHammerName(){
        String name = "Big Hammer";
        hammer.setName(name);
        Assert.assertEquals(name,hammer.getName());
    }
    @Test
    public void checkSetHammerWeight(){
        double weight = 10.15;
        hammer.setWeight(weight);
        Assert.assertEquals(weight,hammer.getWeight(),0.001);
    }
    @Test
    public void checkCompareToEqual(){
        Hammer hammer1 = new Hammer();
        hammer1.setName("Big Hammer");
        hammer.setName("Big Hammer");
        Assert.assertEquals(0, hammer.compareTo(hammer1));
    }
    @Test
    public void checkCompareToLess(){
        Hammer hammer2 = new Hammer();
        hammer2.setName("Angry Hammer");
        hammer.setName("Big Hammer");
        Assert.assertTrue(hammer.compareTo(hammer2) < 0);
    }
    @Test
    public void checkCompareToMore(){
        Hammer hammer3 = new Hammer();
        hammer3.setName("Small Hammer");
        hammer.setName("Big Hammer");
        Assert.assertTrue(hammer.compareTo(hammer3)>0);
    }
}

