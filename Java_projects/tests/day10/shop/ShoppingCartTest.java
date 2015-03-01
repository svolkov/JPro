package day10.shop;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by syv on 26.01.15.
 */
@RunWith(JUnit4.class)
public class ShoppingCartTest extends Assert{
    ShoppingCart sCart;

    @Before
    public void Setup(){
        sCart = new ShoppingCart();
    }

    @Test
    public void checkSCartNotNull(){
        Assert.assertNotNull("Object of the ShoppingCart class hasn't been created",sCart);
    }

    @Test
    public void checkSCartAddItem(){
        Transaction trans = new Transaction();
        sCart.add(trans);
        assertEquals(trans, sCart.getItem(0));
    }

    @Test
    public void checkSCartGetItemIfEmpty(){
         assertNull(null, sCart.getItem(0));
    }

    @Test
    public void checkSCartRemoveItem(){
        Transaction trans = new Transaction();
        sCart.add(trans);
        sCart.removeItem();
        assertNull(null, sCart.getItem(0));
     }

    @Test
    public void checkSCartRemoveAllItems(){
        Transaction trans = new Transaction();
        Transaction trans1 = new Transaction();
        Transaction trans2 = new Transaction();
        sCart.add(trans);
        sCart.add(trans1);
        sCart.add(trans2);
        sCart.removeAll();
        assertNull(null, sCart.getItem(0));
    }

    @Test
    public void checkSCartSum(){
        Transaction trans = new Transaction();
        trans.setPrice(50);
        trans.setQuantity(8);
        Transaction trans1 = new Transaction();
        trans1.setPrice(30);
        trans1.setQuantity(10);
        Transaction trans2 = new Transaction();
        trans2.setPrice(40);
        trans2.setQuantity(5);
        sCart.add(trans);
        sCart.add(trans1);
        sCart.add(trans2);
        assertEquals(900, sCart.getSum(),0.01);
    }

    @Test
    public void checkSCartDiscount0(){
        Transaction trans = new Transaction();
        trans.setPrice(5);
        trans.setQuantity(2);
        Transaction trans1 = new Transaction();
        trans1.setPrice(3);
        trans1.setQuantity(1);
        Transaction trans2 = new Transaction();
        trans2.setPrice(4);
        trans2.setQuantity(5);
        sCart.add(trans);
        sCart.add(trans1);
        sCart.add(trans2);
        assertEquals(0, sCart.getDiscount());
    }

    @Test
    public void checkSCartDiscount5(){
        Transaction trans = new Transaction();
        trans.setPrice(5);
        trans.setQuantity(2);
        Transaction trans1 = new Transaction();
        trans1.setPrice(30);
        trans1.setQuantity(1);
        Transaction trans2 = new Transaction();
        trans2.setPrice(4);
        trans2.setQuantity(5);
        sCart.add(trans);
        sCart.add(trans1);
        sCart.add(trans2);
        assertEquals(5, sCart.getDiscount());
    }

    @Test
    public void checkSCartDiscount10(){
        Transaction trans = new Transaction();
        trans.setPrice(500);
        trans.setQuantity(2);
        Transaction trans1 = new Transaction();
        trans1.setPrice(30);
        trans1.setQuantity(1);
        Transaction trans2 = new Transaction();
        trans2.setPrice(4);
        trans2.setQuantity(5);
        sCart.add(trans);
        sCart.add(trans1);
        sCart.add(trans2);
        assertEquals(10, sCart.getDiscount());
    }

    @Test
    public void checkSCartSumDiscount0(){
        Transaction trans = new Transaction();
        trans.setPrice(5);
        trans.setQuantity(2);
        Transaction trans1 = new Transaction();
        trans1.setPrice(3);
        trans1.setQuantity(1);
        Transaction trans2 = new Transaction();
        trans2.setPrice(4);
        trans2.setQuantity(5);
        sCart.add(trans);
        sCart.add(trans1);
        sCart.add(trans2);
        assertEquals(33, sCart.getEndSum(),0.001);
    }

    @Test
    public void checkSCartSumDiscount5(){
        Transaction trans = new Transaction();
        trans.setPrice(5);
        trans.setQuantity(2);
        Transaction trans1 = new Transaction();
        trans1.setPrice(30);
        trans1.setQuantity(1);
        Transaction trans2 = new Transaction();
        trans2.setPrice(4);
        trans2.setQuantity(5);
        sCart.add(trans);
        sCart.add(trans1);
        sCart.add(trans2);
        assertEquals(57, sCart.getEndSum(),0.001);
    }

    @Test
    public void checkSCartSumDiscount10(){
        Transaction trans = new Transaction();
        trans.setPrice(500);
        trans.setQuantity(2);
        Transaction trans1 = new Transaction();
        trans1.setPrice(30);
        trans1.setQuantity(1);
        Transaction trans2 = new Transaction();
        trans2.setPrice(4);
        trans2.setQuantity(5);
        sCart.add(trans);
        sCart.add(trans1);
        sCart.add(trans2);
        assertEquals(945, sCart.getEndSum(),0.001);
    }

    @Test
    public void checkSCartSize(){
        Transaction trans = new Transaction();
        Transaction trans1 = new Transaction();
        Transaction trans2 = new Transaction();
        sCart.add(trans);
        sCart.add(trans1);
        sCart.add(trans2);
        assertEquals(3, sCart.getSize());
    }
}
