import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by syv on 15.03.15.
 */
public class RinkDemo {
    public static void main(String[] args){
        final int visitorsNumber = 50;
        final SkatingRink skatingRink = new VipSkatingRink();
        final Random random = new Random();
        final CountDownLatch latchStart = new CountDownLatch(10);
        final CountDownLatch latchStop = new CountDownLatch(visitorsNumber);

        for(int i = 0; i < visitorsNumber; i++){
            final Skater skater = new Skater("Skater"+i);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    System.out.println(skater.getName()+" come to rink");
                    try {
                        latchStart.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Skates skates = null;
                    while (skates == null){
                        skates = skatingRink.getSkates(skater);
                        sleep(random.nextInt(1000));
                    }

                    System.out.println(skater.getName()+" is skating");
                    sleep(random.nextInt(5000));
                    skatingRink.returnSkates(skates,skater);
                    latchStop.countDown();
                }
            });
            thread.start();
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            latchStart.countDown();
            sleep(random.nextInt(1000));
        }
        try {
            latchStop.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

