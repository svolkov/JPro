package serviceclasses;

import tanks.AbstractTank;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by syv on 22.02.15.
 */
public class ActionRecorder {

    private File file;
    private File directory;
    FileOutputStream fos;
    ObjectOutputStream oos;

    public ActionRecorder(){
    }

    public void init(AbstractTank tank){

      directory = new File("/home/syv/job_place/TanksRecords");
      if(!directory.exists()){
            directory.mkdir();
      }
      file = new File(directory.getAbsolutePath()+"/"+tank.getClass().getName().substring(tank.getClass().getName().indexOf(".")+1)
              +".bin");
      try {
         if(!file.exists()){
              file.createNewFile();
         }
         fos = new FileOutputStream(file);
         oos = new ObjectOutputStream(fos);
        } catch (IOException e) {
            try {
                oos.close();
                fos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void record(Action act){
        try {
            oos.writeObject(act);
            oos.reset();
        } catch (IOException e) {
            try {
                oos.flush();
                oos.close();
                fos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            oos.writeObject(null);
            oos.flush();
            oos.close();
            fos.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
