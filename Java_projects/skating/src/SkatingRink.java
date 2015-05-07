/**
 * Created by syv on 13.03.15.
 */
public interface SkatingRink {

    public Skates getSkates(Skater skater);

    public void returnSkates(Skates skates, Skater skater);

 //   public boolean isStoreEmpty();
}