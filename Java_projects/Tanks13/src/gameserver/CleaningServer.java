package gameserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by syv on 14.05.15.
 */
public class CleaningServer {

    public static void main(String[] args){
        Socket socket = null;
        ServerSocket ss = null;
        ConcurrentLinkedQueue<Character> queue = new ConcurrentLinkedQueue<>();


        try {
            ss = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("GameServer has run");

        initCommandQueue(queue);

        while (true){
            try {
                socket = ss.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (
                   OutputStream os = socket.getOutputStream()
            ) {
                int data;

                while (!ss.isClosed()){
                    if(!queue.isEmpty()) {
                        data = queue.poll();
                        System.out.println("Command:" + (char) data);
                        os.write(data);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private static void initCommandQueue(ConcurrentLinkedQueue<Character> queue){
        for(int i = 0; i < 8; i++) {
            queue.add('F');
        }
        queue.add('R');
        for(int i = 0; i < 8; i++) {
            queue.add('F');
        }
        for(int i = 0; i < 8; i++){
            queue.add('U');
            queue.add('L');
            for(int n = 0; n < 8; n++) {
                queue.add('F');
            }
            queue.add('R');
        }
    }
}
