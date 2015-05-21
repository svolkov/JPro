package gameserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by syv on 16.05.15.
 */
public class Server extends Thread {
    private Socket socket;
    private ConcurrentLinkedQueue<Character> queue;
    private AtomicBoolean readyToSend;

    public Server( Socket socket){
      this.socket = socket;
      queue = new ConcurrentLinkedQueue<>();
      readyToSend.set(true);
    }

    public AtomicBoolean getReadyToSend() {
        return readyToSend;
    }

    public void setReadyToSend(AtomicBoolean readyToSend) {
        this.readyToSend = readyToSend;
    }

    public void startCommandReceiver(){
        InputStream is;
        try {
            is = socket.getInputStream();

            System.out.println("GameServer: CommandReceiver is ready");
            int data;

            while ((data = is.read())!=-1){
                queue.add((char)data);
                System.out.println("Received command:" + (char) data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   public void startCommandSender(){
        try {
            OutputStream os = socket.getOutputStream();

            System.out.println("GameServer: CommandSender is ready");
            int data;

            while (!socket.isClosed()){
                if(!queue.isEmpty() ) {
                    data = queue.poll();
                    System.out.println("Sent command:" + (char) data);
                    os.write(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
