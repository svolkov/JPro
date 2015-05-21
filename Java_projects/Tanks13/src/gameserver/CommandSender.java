package gameserver;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by syv on 18.05.15.
 */
public class CommandSender extends Thread {

    private OutputStream osMaster;
    private OutputStream osSlave;
    private ConcurrentLinkedQueue<Character> queueOut;

    public CommandSender(OutputStream osMaster, ConcurrentLinkedQueue<Character> queueOut){
      this.osMaster = osMaster;
      this.queueOut = queueOut;
    }
    @Override
    public void run() {
        try {
            System.out.println("GameServer: CommandSender is ready");
            int data;

            while (true){
                if(!queueOut.isEmpty()) {
                    data = queueOut.poll();
                    System.out.println("Sent command:" + (char) data);
                    osMaster.write(data);
                    osSlave.write(data);
                }
                Thread.sleep(100);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public OutputStream getOsMaster() {
        return osMaster;
    }

    public void setOsSlave(OutputStream osSlave) {
        this.osSlave = osSlave;
    }
}
