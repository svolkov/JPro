package day13.shop;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by syv on 05.05.15.
 */
public class ExternalCustomersDB {
    private static final int BUFFER_SIZE = 12;

    public static void main(String[] args){
        LinkedList<Customer> customers = new LinkedList<>();

        String data = receiveData();
        decodingData(data, customers);
        displayData(customers);
    }

    private static String receiveData(){
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

        StringBuffer stringBuffer = new StringBuffer();
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("localhost", 8080));
            System.out.println("Connection to Shop's Server has been established.");

            int receivedBytes = socketChannel.read(buffer);
            while(receivedBytes != -1) {
                buffer.flip();

                while(buffer.hasRemaining()){
                    stringBuffer.append((char) buffer.get());
                }
                buffer.clear();
                receivedBytes = socketChannel.read(buffer);
            }
         //   System.out.println("Received:"+receivedBytes+" bytes: "+ stringBuffer);
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return stringBuffer.toString();
    }

    private static void decodingData(String incomeData, LinkedList<Customer> list){
        Customer customer;

        while(!incomeData.isEmpty()) {
            customer = new Customer();
            customer.setLogin(incomeData.substring(0, incomeData.indexOf(']')));
            incomeData = incomeData.substring(incomeData.indexOf(']')+1);
            customer.setEmail(incomeData.substring(0, incomeData.indexOf(']')));
            incomeData = incomeData.substring(incomeData.indexOf(']')+1);
            list.add(customer);
        }
    }

    private static void displayData(LinkedList<Customer> list){
       Iterator<Customer> iter = list.iterator();
       Customer customer;

       System.out.println("List of customers:");

        while (iter.hasNext()){
            customer = iter.next();
            System.out.println("Name: " + customer.getLogin() + ", e-mail: " + customer.getEmail());
        }
    }
}
