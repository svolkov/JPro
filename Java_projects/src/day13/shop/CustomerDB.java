package day13.shop;

import java.util.LinkedList;

/**
 * Created by syv on 03.05.15.
 */
public class CustomerDB {
    private LinkedList<Customer> customersList;

    public CustomerDB(){
        customersList = new LinkedList<>();
    }

    public void add(Customer customer){
        customersList.add(customer);
    }

    public Customer get(int index){
       return customersList.get(index);
    }

    public int size(){
        return customersList.size();
    }
}
