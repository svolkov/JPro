import com.sun.scenario.effect.impl.prism.ps.PPSDrawable;

import java.awt.*;

/**
 * Created by syv on 08.03.15.
 */
public class FlyingBall extends Thread{
    private Color color;
    private int x;
    private int y;
    private int maxX;
    private boolean direction;
    private int step;


//    private static final int RADIUS = 20;

    public FlyingBall(Color color, int maxX, int y, int step){
        this.color = color;
        this.y = y;
        this.maxX = maxX;
        this.step = step;
        direction = true;

    }


    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void go(){

        while(true) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (direction) {
               if (x >= maxX) {
                   direction = false;
                   x -= step;
               } else {
                   x += step;
               }
           } else {
               if (x <= 0) {
                   direction = true;
                   x += step;
               } else {
                   x -= step;
               }
           }
       }
    }

//    public void draw(Graphics g){
//
//        g.setColor(color);
//        g.fillOval(x,y,RADIUS,RADIUS);
//
//    }

    @Override
    public void run() {
        go();
    }
}
