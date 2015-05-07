/**
 * Created by syv on 17.03.15.
 */
public class DemoIdGen {


    public static void main(String[] args){
        final IdGenerator idGenerator = new IdGenerator();
        for (int i = 0; i<100; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    long id = idGenerator.getNextId();
                    System.out.println("Thread"+Thread.currentThread().getName()+" id: "+id);
                }
            }).start();
        }
    }
}
