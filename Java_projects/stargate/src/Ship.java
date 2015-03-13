import java.awt.*;

/**
 * Created by syv on 11.03.15.
 */
public class Ship{

        private Color color;
        private int x;
        private int y;
        private int gateX;
        private boolean closelyToGate;
        private int step;


       private static final int RADIUS = 20;

        public Ship(){
            color = Color.BLUE;
            y = 150;
            x = 0;
            step = 1;
            closelyToGate = false;

        }

    public void setX(int x) {
        this.x = x;
    }

    public void setGateX(int gateX) {
        this.gateX = gateX;
    }

    public void setCloselyToGate(boolean closelyToGate) {
        this.closelyToGate = closelyToGate;
    }

    public boolean isCloselyToGate() {
        return closelyToGate;
    }

    public int getX() {
            return x;
        }

    public void go(){
            x += step;
            try {
                    Thread.sleep(1000/75);
            } catch (InterruptedException e) {
                    e.printStackTrace();
            }

            if (x >= gateX){
                closelyToGate = true;
            }
        }

    public void draw(Graphics g){
        g.setColor(color);
        g.fillOval(x,y,RADIUS,RADIUS);
    }

}
