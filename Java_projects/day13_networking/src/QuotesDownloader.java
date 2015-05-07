
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by syv on 18.04.15.
 */
public class QuotesDownloader {
    public static void main(String[] args) {
        URL myURL = null;
        URLConnection urlConnection = null;
        InputStreamReader isReader = null;
        BufferedReader bufferedReader = null;

        try {
            myURL = new URL("http://download.finance.yahoo.com/d/quotes.csv?s=NOK&f=sl1d1t1c1ohgv&e=.csv");
        } catch (MalformedURLException e) {
            System.out.println("Could not open URL " + e);
        }
        if (myURL != null) {
            try {
                urlConnection = myURL.openConnection();
                isReader = new InputStreamReader(urlConnection.getInputStream());
                bufferedReader = new BufferedReader(isReader);

                String quoteData = bufferedReader.readLine();

                System.out.println(quoteData);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferedReader.close();
                    isReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}