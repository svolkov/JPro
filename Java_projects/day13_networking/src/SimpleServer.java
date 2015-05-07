import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by syv on 10.04.15.
 */
public class SimpleServer {
    static Socket socket;
    public static void main(String[] args) throws IOException {

            ServerSocket ss = new ServerSocket(8080);

            while (true){
              socket = ss.accept();
              new Thread(new Runnable() {
                  @Override
                  public void run() {
                      try (
                              InputStream is = socket.getInputStream();
                              OutputStream os = socket.getOutputStream()
                      ) {
                          int data;
                          while ((data = is.read())!=-1){
                              data = transmogrify(data);
                              os.write(data);
                          }
                      } catch (IOException e) {
                          e.printStackTrace();
                      }
                  }
              }).start();

            }
    }

    private static int transmogrify(int d){
        if(Character.isLetter(d)){
            return d ^ ' ';
        }
        return d;
    }
}
