package gameclient;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

/**
 * Created by syv on 12.05.15.
 */
public class GameClient {

    public static void main(String[] args){
        try {
            Socket socket = new Socket("localhost",8080);
            OutputStream os = socket.getOutputStream();

            os.write(generateControllingData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] generateControllingData(){

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("FFFFFFFFRFFFFFFFF");

        for(int i = 0; i < 8; i++){
            stringBuffer.append("ULFFFFFFFFR");;
        }

        return stringBuffer.toString().getBytes();
    }
}
