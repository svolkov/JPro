package serviceclasses;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by syv on 09.05.15.
 */
public class Server extends Thread{
  private Socket socket;
  private ServerSocket ss;
  private ConcurrentLinkedQueue<Character> queue;

   public Server( ConcurrentLinkedQueue<Character> queue){
       this.queue = queue;
       try {
          ss = new ServerSocket(8080);
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    @Override

    public void run(){
    System.out.println("GameServer has run");
       while (true){
        try {
            socket = ss.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (
              InputStream is = socket.getInputStream();
       //       OutputStream os = socket.getOutputStream()
                ) {

                    int data;
                    while ((data = is.read())!=-1){
                       queue.add((char)data);
                        System.out.println("Command:" +(char)data);
         //               os.write(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }
}

