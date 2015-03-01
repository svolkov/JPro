package serviceclasses;

import tanks.AbstractTank;

import java.io.*;

/**
 * Created by syv on 23.02.15.
 */
public class ActionPlayer {

    private File file;
    private File directory;
    FileInputStream fis;
    ObjectInputStream ois;

    public ActionPlayer(){
    }

    public void init(AbstractTank tank){

        directory = new File("/home/syv/job_place/TanksRecords");
        if(!directory.exists()){
            System.out.println("Directory "+directory.getAbsolutePath()+" not found");
            System.exit(0);
        }
        file = new File(directory.getAbsolutePath()+"/"+tank.getClass().getName().substring(tank.getClass().getName().indexOf(".")+1)
                +".bin");
        if(!file.exists()){
            System.out.println("File "+file.getAbsolutePath()+" not found");
            System.exit(0);
        }
        try {
            fis = new FileInputStream(file);
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

