import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by syv on 13.03.15.
 */
public class SchoolSkatingRink implements SkatingRink{
    List<Skates> store;
    Queue<Skater> skaterQueue;

    public SchoolSkatingRink(){
        skaterQueue = new LinkedList<Skater>();
        store = new LinkedList<Skates>();
        for(int i = 0; i < 3; i++) {
            store.add(new Skates());
        }
    }
    @Override
    public synchronized Skates getSkates(Skater skater) {

            if (store.isEmpty()) {
                skaterQueue.offer(skater);
                System.out.println(skater.getName() + " didn't get skates");
                return null;
            } else {
                System.out.println(skater.getName() + " got skates");
                return store.remove(store.size() - 1);
            }
    }

    @Override
    public /*synchronized*/ void returnSkates(Skates skates, Skater skater) {

          store.add(skates);
          System.out.println(skater.getName() + "returned skates");
          synchronized (this) {
              if (!skaterQueue.isEmpty()) {
                  Skater firstSkaterInQueue = skaterQueue.poll();
                  synchronized (firstSkaterInQueue) {
                      firstSkaterInQueue.notify();
                  }
              }
          }
    }

}
