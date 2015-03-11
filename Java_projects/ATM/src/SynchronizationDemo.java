import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by syv on 09.03.15.
 */
public class SynchronizationDemo {

    public static void main(String[] args){
        Random random = new Random();

        ATM atm = new ATM();
        int loginWife = 1122;
        int loginHusband = 2211;

        List<ActionATM> actionATMs = new ArrayList<ActionATM>();

        for (int i = 0; i<30; i++){
            actionATMs.add(new ActionATM(atm,loginHusband,random.nextInt(1000)));
            actionATMs.add(new ActionATM(atm,loginWife,random.nextInt(1000)));
        }

        for (ActionATM action : actionATMs){
            new Thread(action).start();
        }
    }
}
