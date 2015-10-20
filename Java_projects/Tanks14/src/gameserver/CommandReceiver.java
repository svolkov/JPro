package gameserver;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by syv on 18.05.15.
 */
public class CommandReceiver extends Thread  {
   private ConcurrentLinkedQueue<Character> queueMaster;

   private InputStream is;

   public CommandReceiver(InputStream is,ConcurrentLinkedQueue<Character> queueMaster){//, ConcurrentLinkedQueue<Character> queueSlave){
     this.queueMaster = queueMaster;
     this.is = is;
   }

   @Override
   public void run() {
        try {
            System.out.println("GameServer: CommandReceiver is ready");
            int data;
            while ((data = is.read())!=-1){
                queueMaster.add((char)data);
                System.out.println("Received command:" + (char) data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
