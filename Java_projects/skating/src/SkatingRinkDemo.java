import java.util.Random;

/**
 * Created by syv on 13.03.15.
 */
public class SkatingRinkDemo {
    public static void main(String[] args){

        final SkatingRink skatingRink = new SchoolSkatingRink();
        final Random random = new Random();

        for(int i = 0; i < 100; i++){
            final Skater skater = new Skater("Skater"+i);
            new Thread(new Runnable() {
                @Override
                public void run() {

                  System.out.println(skater.getName()+" come to rink");
                  Skates skates;

                    if ((skates = skatingRink.getSkates(skater)) == null){
                       synchronized (skater) {
                           try {
                               skater.wait();
                               skates = skatingRink.getSkates(skater);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       }
                    }

                  System.out.println(skater.getName()+" is skating");
                  sleep(random.nextInt(5000));
                  skatingRink.returnSkates(skates,skater);
                }
            }).start();
            sleep(random.nextInt(1000));
        }
    }
   private static void sleep(int timeout){
       try {
           Thread.currentThread().sleep(timeout);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
   }
}
