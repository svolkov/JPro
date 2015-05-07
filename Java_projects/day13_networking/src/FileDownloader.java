import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by syv on 14.04.15.
 */
public class FileDownloader {
    public static void main(String[] args) {
        URL myURL = null;
        InputStream is = null;
        FileOutputStream fos = null;

        File file = new File("/home/syv/job_place/downloads/instruction.pdf");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            myURL = new URL("http://www.irs.gov/pub/irs-pdf/i1040ez.pdf");
        } catch (MalformedURLException e) {
            System.out.println("Could not open URL " + e);
        }
        if (myURL != null) {
            try {
                URLConnection urlConnection = myURL.openConnection();
                is = urlConnection.getInputStream();
                fos = new FileOutputStream(file);
                int data;

                System.out.println("File is downloaded");
                while ((data=is.read())!=-1){
                    fos.write(data);
                }
                System.out.println("File downloaded");
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    is.close();
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
