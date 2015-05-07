import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by syv on 13.04.15.
 */
public class Utils {
    public static int transmogrify(int d){
        if(Character.isLetter(d)){
            return d ^ ' ';
        }
        return d;
    }

    public static void process(SocketChannel sc){
        System.out.println("Connection from "+sc);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        try {
            while (sc.read(byteBuffer)!=-1){
                byteBuffer.flip();
                for(int i = 0; i < byteBuffer.limit();i++){
                    byteBuffer.put(i,(byte) transmogrify(byteBuffer.get(i)));
                }
                sc.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (IOException e) {
            System.out.println("Problem with Connection "+e);
        }
    }
}
