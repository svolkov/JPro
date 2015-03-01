package day11.zipfiles;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by syv on 16.02.15.
 */
public class Unzip {
    public static void main(String[] args){
        int numberReceived;
        byte[] buffer = new byte[1024];
        ZipEntry zipEntry;
        String dirName;

        File inFile = new File(args[0]);//"/home/syv/job_environment/ZipFiles.zip");

        if(args.length==0){
            System.out.println("Error: Input file name .zip");
            System.exit(0);
        }
        String unzipDirName = inFile.getParent();

        try(
                BufferedInputStream ios = new BufferedInputStream(new FileInputStream(inFile));//"/home/syv/job_environment/ZipFiles.zip"));
                ZipInputStream zis = new ZipInputStream(ios);
        ) {
            while((zipEntry = zis.getNextEntry())!=null){
                File zipFile = new File(zipEntry.getName());
                dirName = unzipDirName.concat("/"+zipFile.getParent());

                if(dirName!=null) {
                    File dir = new File(dirName);
                    if (!dir.exists()){
                       checkDirectory(dir);
                       dir.mkdir();
                    }
                }
                File unzipFile = new File(dirName+"/"+zipFile.getName());
                unzipFile.createNewFile();

                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(unzipFile));
                while ((numberReceived = zis.read(buffer,0,buffer.length))!=-1){
                    bos.write(buffer,0,numberReceived);
                }
                dirName = null;
                bos.close();
                zis.closeEntry();
            }

            zis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("OK");
    }

    private static void checkDirectory(File fileDir){
        String dirName = fileDir.getParent();

        if(dirName!=null) {
            File dir = new File(dirName);
            if (!dir.exists()){
                checkDirectory(dir);
                dir.mkdir();
            }
        }
    }
}
