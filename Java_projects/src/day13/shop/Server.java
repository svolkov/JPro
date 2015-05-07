package day13.shop;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * Created by syv on 05.05.15.
 */
public class Server extends Thread  {
    private final int BUFFER_SIZE = 48;

    private CustomerDB customerDB;
    private ServerSocketChannel serverSocketChannel;
    private SocketChannel socketChannel;
    private ByteBuffer buffer;

    public Server(CustomerDB customerDB){
        this.customerDB = customerDB;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("localhost",8080));
            buffer = ByteBuffer.allocate(BUFFER_SIZE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        System.out.println("Shop's server has started");
        while (true){
            try {
                socketChannel = serverSocketChannel.accept();
                System.out.println("Shop's server has accepted connection");

        // Get data to be sent as String

        // Send data
            dataTransfer(dataPreparation());//"!!!!Hello!!!!0123456789012345678901234567890123456789");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String dataPreparation(){
        StringBuffer stringBuffer = new StringBuffer();
        if(customerDB.size()==0){
            stringBuffer.append('#');
        }else{
            for(int i = 0; i < customerDB.size(); i++){
                stringBuffer.append(customerDB.get(i).getLogin());
                stringBuffer.append(']');
                stringBuffer.append(customerDB.get(i).getEmail());
                stringBuffer.append(']');
            }
        }
        return stringBuffer.toString();
    }

    private void dataTransfer(String data){
       boolean dataSent = false;

       do {
           if(data.length()>=BUFFER_SIZE){
               sendBuffer(data.substring(0,BUFFER_SIZE-1));
               data = data.substring(BUFFER_SIZE);
               if(data == ""){
                   dataSent = true;
               }
           }else {
               sendBuffer(data);
               dataSent = true;
           }
       }
       while(!dataSent);
        try {
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBuffer(String dataPortion){

        buffer.put(dataPortion.getBytes());

        buffer.flip();
        try {
            socketChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer.clear();
    }
}
