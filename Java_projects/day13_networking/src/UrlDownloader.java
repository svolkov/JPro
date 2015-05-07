import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by syv on 14.04.15.
 */
public class UrlDownloader {

    public static void main(String[] args){
        URL myURL = null;
        try {
            myURL = new URL("http://www.kademika.com/");
        } catch (MalformedURLException e) {
            System.out.println("Could not open URL " + e);
        }
        if (myURL != null) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(myURL.openStream()));
                String inputLine;
                while ((inputLine=bufferedReader.readLine())!=null){
                    System.out.println(inputLine);
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
