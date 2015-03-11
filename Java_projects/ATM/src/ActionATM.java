/**
 * Created by syv on 09.03.15.
 */
public class ActionATM implements Runnable{

    private ATM atm;
    private int login;
    private int sum;

    public ActionATM(ATM atm,int login, int sum) {
        this.atm = atm;
        this.login = login;
        this.sum = sum;
    }

    @Override
    public void run() {
        atm.checkBalance(login);
        atm.withdrawMoney(login,sum);
    }
}
