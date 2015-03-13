import java.awt.*;

/**
 * Created by syv on 11.03.15.
 */
public class Gate {
    private static final int OPEN_SHIFT = 80;

    private Color color;
    private int x;
    private int y1, y2;
    private int step;
    private boolean closed;

    public Gate(){
        color = Color.red;
        x = 300;
        y1 = 100;
        y2 = 160;
        step = 1;
        closed = true;
    }

    public void draw(Graphics g){
        g.setColor(color);
        g.fillRect(x,y1,20,60);
        g.fillRect(x,y2,20,60);
    }

    public void open() {

        while (closed) {
          try {
            Thread.sleep(1000/60);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          y1 -= step;
          y2 += step;
          if (y1 <= OPEN_SHIFT) {
              closed = false;
          }
        }
    }

    public void close() {

        while (!closed) {
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            y1 += step;
            y2 -= step;
            if (y1 >= 100) {
                closed = true;
            }
        }
    }
}
