package gameserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by syv on 13.05.15.
 */
public class GameServer {
    public static void main(String[] args){
        Socket socket = null;
        ServerSocket ss = null;
        InputStream is = null;
        OutputStream os = null;
        final ConcurrentLinkedQueue<Character> queueAggressor = new ConcurrentLinkedQueue<>();
        final ConcurrentLinkedQueue<Character> queueDefender = new ConcurrentLinkedQueue<>();

        final AtomicBoolean defenderReadyToSend = new AtomicBoolean();
        final AtomicBoolean aggressorReadyToSend = new AtomicBoolean();

        boolean defenderExist = false;
        boolean aggressorExist = false;

        defenderReadyToSend.set(true);
        aggressorReadyToSend.set(true);
       // final Dispatcher dispatcher = new Dispatcher();

        try {
            ss = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("GameServer has run");

        while (true) {
            try {
                socket = ss.accept();
                is = socket.getInputStream();
                os = socket.getOutputStream();
                char greetingCode;
                do {
                    greetingCode = (char) is.read();
                }
                while (greetingCode != 'a' && greetingCode != 'd');

                if (greetingCode == 'a' && !aggressorExist) {
                    os.write('y');
                    aggressorExist = true;
                    final InputStream finalIs = is;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            commandReceiver(finalIs, queueAggressor);
                        }
                    }).start();

                    final OutputStream finalOs = os;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            commandSender(finalOs,queueAggressor,aggressorReadyToSend,defenderReadyToSend);
                        }
                    }).start();
                    System.out.println("Aggressor connected");
                } else {
                    if (greetingCode == 'd' && !defenderExist) {
                        os.write('y');
                        defenderExist = true;
                        final InputStream finalIs1 = is;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                commandReceiver(finalIs1, queueDefender);
                            }
                        }).start();

                        final OutputStream finalOs1 = os;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                commandSender(finalOs1,queueDefender, defenderReadyToSend,aggressorReadyToSend);
                            }
                        }).start();
                        System.out.println("Defender connected");
                    }else{
                        os.write('n');
                        System.out.println("Connection was not agreed");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
          //  final Socket finalSocket = socket;
        }
    }
   private static void commandReceiver(InputStream is, ConcurrentLinkedQueue<Character> queue){

       try {
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

    private static void commandSender(OutputStream os, ConcurrentLinkedQueue<Character> queue, AtomicBoolean thisSenderReady,
                                      AtomicBoolean anotherSenderReady){
        try {
            System.out.println("GameServer: CommandSender is ready");
            int data;

 //           while (!socket.isClosed()){
              while (true){
                if(!queue.isEmpty() && thisSenderReady.get()) {
                    anotherSenderReady.set(false);
                    data = queue.poll();
                    System.out.println("Sent command:" + (char) data);
                    os.write(data);
                    thisSenderReady.set(false);
                    anotherSenderReady.set(true);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
