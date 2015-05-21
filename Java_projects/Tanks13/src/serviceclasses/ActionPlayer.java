package serviceclasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by syv on 23.02.15.
 */
public class ActionPlayer {

    private File file;
    private File directory;
    private File[] tankRecordsFiles;
    FileInputStream fis;
    ObjectInputStream ois;

    public ActionPlayer(){
    }

    public void init(){

        directory = new File("/home/syv/job_place/TanksRecords");
        if(!directory.exists()){
            System.out.println("Directory "+directory.getAbsolutePath()+" not found");
            System.exit(0);
        }
        tankRecordsFiles = directory.listFiles();
        if(tankRecordsFiles.length == 0){
            System.out.println("File with TankRecords not found");
            System.exit(0);
        }
//        file = new File(directory.getAbsolutePath()+"/"+tank.getClass().getName().substring(tank.getClass().getName().indexOf(".")+1)
//                +".bin");
//        if(!file.exists()){
//            System.out.println("File "+file.getAbsolutePath()+" not found");
//            System.exit(0);
//        }
        try {
            fis = new FileInputStream(tankRecordsFiles[0]);//file);
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            try {
                ois.close();
                fis.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public Action read(){
        Action act = null;
        try {
            act=(Action)ois.readObject();
        } catch (IOException e) {
            try {
                ois.close();
                fis.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return act;
    }

    public void close(){
        try {
            //ois.flush();
            ois.close();
            fis.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}

