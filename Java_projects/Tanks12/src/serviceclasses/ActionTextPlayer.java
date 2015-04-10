package serviceclasses;

import tanks.AbstractTank;
import tanks.BT7;
import tanks.T34;
import tanks.Tiger;

import java.io.*;

/**
 * Created by syv on 26.02.15.
 */
public class ActionTextPlayer {

    private File file;
    private File directory;
    private FileReader fileReader;
    private String[] fileNames;
    private char tankCode;
    private AbstractTank tankDefender, tankAggressor;

    public ActionTextPlayer(){
    }

    public void init(AbstractTank tankDefender, AbstractTank tankAggressor){
        this.tankDefender = tankDefender;
        this.tankAggressor = tankAggressor;
        checkIfFileExist();

        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            try {
                fileReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    public Action read() {
        char[] actionCode = new char[5];

        try {
          if(fileReader.read(actionCode)!=-1){
              Action action = defineTankAction(actionCode[0]);
               decodeAction(action,actionCode);
               return action;
          }
        } catch (IOException e) {
            try {
                fileReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return null;
    }

    public void close(){
        try {
            fileReader.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void checkIfFileExist(){

        directory = new File("/home/syv/job_place/TanksRecords");
        if(!directory.exists()){
            System.out.println("Directory "+directory.getAbsolutePath()+" not found");
            System.exit(0);
        }
       // fileNames = new String[];
        fileNames=directory.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.startsWith("tankrec")){
                    return true;
                }else{
                    return false;
                }
            }
        });
        if(fileNames.length>0) {
            file = new File(directory.getAbsolutePath() + "/" + fileNames[0]);
        }else{
            System.out.println("File " +directory.getAbsolutePath() + "/" + fileNames[0]  + " not found");
            System.exit(0);
         }
    }

    private Action defineTankAction(char code){
        Action result = null;

        switch (code){
            case 'a' : result = tankDefender.action;
                       break;
            case 'b' : result = tankAggressor.action;
                break;
            case 'c' : result = tankAggressor.action;
                break;
            default: throw new IllegalArgumentException("Illegal type of tank");
        }

        return result;
    }

    private void decodeAction(Action action, char[] code){

        action.setNextAct(decodeNextAct(code[1]));
        action.setPrevTurnAct(decodePrevTurnAct(code[2]));
        action.setActionResult(decodeActResult(code[3]));
        action.setAttemptCount(decodeAttemptsCount(code[4]));

    }

    private TankActions decodeNextAct(char code){
        TankActions result = null;

        switch (code){
            case 'f'      :result=TankActions.FIRE;
                           break;
            case 'm'      :result= TankActions.MOVE;
                           break;
            case 'r'      :result= TankActions.TURN_RIGHT;
                           break;
            case 'l'      :result= TankActions.TURN_LEFT;
                           break;
            default       :throw new IllegalArgumentException("Bad input code: tank action");
        }
        return result;
    }

    private TankActions decodePrevTurnAct(char code){
        TankActions result = null;

        switch (code){
            case 'R'      :result= TankActions.TURN_RIGHT;
                break;
            case 'L'      :result= TankActions.TURN_LEFT;
                break;
            default       :throw new IllegalArgumentException("Bad input code: previous turn action");
        }
        return result;
    }

    private boolean decodeActResult(char code){
        boolean result;

        if(code == '0'){
            result = false;
        }else {
            if (code == '1') {
                result = true;
            } else {
                throw new IllegalArgumentException("Bad input code: result action");
            }
        }
        return result;
    }

    private int decodeAttemptsCount(char code){
        return Character.getNumericValue(code);
    }
}
