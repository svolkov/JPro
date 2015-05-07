import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by syv on 18.03.15.
 */
public class RinkExecServDemo {
    public static void main(String[] args) {
        final int visitorsNumber = 50;
        final SkatingRink skatingRink = new VipSkatingRink();
        final Random random = new Random();
        List<Future> threads = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(visitorsNumber);

        for (int i = 0; i < visitorsNumber; i++) {
            final Skater skater = new Skater("Skater" + i);
            threads.add(executor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(skater.getName() + " come to rink");

                    Skates skates = null;
                    while (skates == null) {
                        skates = skatingRink.getSkates(skater);
                        sleep(random.nextInt(1000));
                    }
                    System.out.println(skater.getName() + " is skating");
                    sleep(random.nextInt(5000));
                    skatingRink.returnSkates(skates, skater);
                }
            }));
        }

        for (Future f : threads){
            try {
                f.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        System.out.println("Rink closed");

    }
        private static void sleep(int timeout){
            try {
                Thread.currentThread().sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
}
