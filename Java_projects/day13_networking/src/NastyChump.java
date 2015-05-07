/**
 * Created by syv on 13.04.15.
 */

import java.io.IOException;
import java.net.Socket;
public class NastyChump {

    public static void main(String[] arg){

        for(int i = 0; i < 3000; i++){
            try {
                new Socket("localhost",8080);
                System.out.println(i);
            } catch (IOException e) {
                System.out.println("Could not connect " + e);
            }
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
