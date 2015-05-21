package gameserver;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by syv on 16.05.15.
 */
public class Dispatcher {
  public AtomicBoolean defenderReadyToSend;
  public AtomicBoolean aggressorReadyToSend;

  public Dispatcher(){
      defenderReadyToSend.set(true);
      aggressorReadyToSend.set(true);
  }
}
