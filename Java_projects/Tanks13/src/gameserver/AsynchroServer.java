package gameserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by syv on 18.05.15.
 */
public class AsynchroServer {
    public static void main(String[] args){
        Socket socket = null;
        ServerSocket ss = null;
        InputStream is = null;
        OutputStream os = null;

        CommandReceiver aggressorReceiver = null;
        CommandSender aggressorSender = null;
        CommandSender aggressorSenderSlave = null;

        CommandReceiver defenderReceiver = null;
        CommandSender defenderSender = null;
        CommandSender defenderSenderSlave = null;

        ConcurrentLinkedQueue<Character> queueMasterAggressor = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<Character> queueMasterDefender = new ConcurrentLinkedQueue<>();

        boolean defenderExist = false;
        boolean aggressorExist = false;
        boolean defenderSlaveExist = false;
        boolean aggressorSlaveExist = false;

        try {
            ss = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("GameServer has run");

        while (!aggressorExist || !defenderExist || !aggressorSlaveExist || !defenderSlaveExist ) {
            try {
                socket = ss.accept();
                is = socket.getInputStream();
                os = socket.getOutputStream();
                char greetingCode;

                do {
                    greetingCode = (char) is.read();
                }
                while (greetingCode != 'a' && greetingCode != 'd' && greetingCode != 'A' && greetingCode != 'D');

                if (greetingCode == 'A' && !aggressorExist) {
                    aggressorExist = true;
                    aggressorReceiver = new CommandReceiver(is,queueMasterAggressor);
                    aggressorSender = new CommandSender(os,queueMasterAggressor);
                    os.write('y');
                    System.out.println("Master Aggressor connected");
                }else{
                    if (greetingCode == 'D' && !defenderExist) {
                        defenderExist = true;
                        defenderReceiver = new CommandReceiver(is,queueMasterDefender);
                        defenderSender = new CommandSender(os,queueMasterDefender);
                        os.write('y');
                        System.out.println("Master Defender connected");
                    }else {
                        if (greetingCode == 'a'){
                            aggressorSlaveExist = true;
                            aggressorSenderSlave = new CommandSender(os,queueMasterAggressor);
                            os.write('y');
                            System.out.println("Slave Aggressor connected");
                        }else{
                            if (greetingCode == 'd'){
                                defenderSlaveExist = true;
                                defenderSenderSlave = new CommandSender(os,queueMasterDefender);
                                os.write('y');
                                System.out.println("Slave Defender connected");
                            }else{
                                os.write('n');
                                System.out.println("Connection was not agreed");
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        aggressorSender.setOsSlave(aggressorSenderSlave.getOsMaster());
        defenderSender.setOsSlave(defenderSenderSlave.getOsMaster());

        aggressorReceiver.start();
        aggressorSender.start();
        defenderReceiver.start();
        defenderSender.start();

        System.out.println("Start game");
    }
}
