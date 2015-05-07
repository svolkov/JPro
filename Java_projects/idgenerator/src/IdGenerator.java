import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by syv on 17.03.15.
 */
public class IdGenerator {
    private AtomicLong id;

    public IdGenerator(){
        id = new AtomicLong();
        id.set(0);
    }

    public long getNextId(){
      return id.getAndIncrement();
    }
}
