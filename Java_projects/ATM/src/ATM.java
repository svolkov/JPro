import java.util.concurrent.locks.Lock;

/**
 * Created by syv on 09.03.15.
 */
public class ATM {
    private int account;
    private Lock lock;

    public ATM(){}

    public void setAccount(int account) {
        this.account = account;
    }

    public void withdrawMoney(int login, int sum){
       if(allowWithdrawal(login)){
           updateAtmAccount(login,sum);
       }
    }

    private boolean allowWithdrawal(int login){
        return true;
    }

    public void checkBalance(int login){
       // synchronized (this){
        lock.lock();
            System.out.println(login+" is going to withdraw some money");
       // }
        lock.unlock();
    }

    private synchronized void updateAtmAccount(int login, int sum){
        System.out.println("Successful operation by account "+login+" , sum: "+sum);
    }
}
