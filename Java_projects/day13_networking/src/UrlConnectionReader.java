import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by syv on 14.04.15.
 */
public class UrlConnectionReader {
    public static void main(String[] args){
        URL myURL = null;
        BufferedReader bufferedReader = null;
        try {
            myURL = new URL("http://www.kademika.com/");
        } catch (MalformedURLException e) {
            System.out.println("Could not open URL " + e);
        }
        if (myURL != null) {
            try {
                URLConnection urlConnection = myURL.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                while ((inputLine=bufferedReader.readLine())!=null){
                    System.out.println(inputLine);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
    }
}
