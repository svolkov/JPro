import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by syv on 15.03.15.
 */
public class VipSkatingRink implements SkatingRink{
    //private List<Skates> store;
    private Queue<Skates> store;
   // private Lock storeLock;

    public VipSkatingRink(){
        store = new LinkedBlockingQueue<Skates>();
        for(int i = 0; i < 3; i++) {
            store.add(new Skates());
        }
  //      storeLock = new ReentrantLock();
    }
    @Override
    public synchronized Skates getSkates(Skater skater) {

//        if (store.isEmpty()) {
//            synchronized (store) {
//                try {
//                    System.out.println(skater.getName() + " didn't get skates");
//                    store.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
  //      storeLock.lock();
       // Skates skates = store.remove(store.size() - 1);
//        Skates skates = store.poll();
    //    storeLock.unlock();


        Skates skates = store.poll();
        if(skates != null){
           System.out.println(skater.getName() + " got skates");
        }
        return skates;
    }

    @Override
    public void returnSkates(Skates skates, Skater skater) {

        //store.add(skates);
        store.offer(skates);
        System.out.println(skater.getName() + "returned skates");
//        synchronized (store) {
//           store.notify();
//        }
    }

}
