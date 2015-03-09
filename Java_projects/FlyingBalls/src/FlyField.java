import javax.swing.*;
import javax.swing.WindowConstants;
import java.awt.*;

/**
 * Created by syv on 08.03.15.
 */
public class FlyField extends JPanel {
    private FlyingBall fBall1, fBall2, fBall3, fBall4, fBall5;
    private JFrame f;

    private static final int RADIUS = 10;
    private static final int MAX_BORDER_X = 500;
    private static final int MAX_BORDER_Y = 400;

    public FlyField(){
        f = new JFrame("Flying Balls");
        f.setLocation(100, 100);
        f.setMinimumSize(new Dimension(MAX_BORDER_X, MAX_BORDER_Y));
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.getContentPane().add(this);
        f.setVisible(true);
    }

    public void startUp(){

        fBall1 = new FlyingBall(Color.BLUE,MAX_BORDER_X-RADIUS,10,1);
        fBall2 = new FlyingBall(Color.green,MAX_BORDER_X-RADIUS,30,2);
        fBall3 = new FlyingBall(Color.cyan,MAX_BORDER_X-RADIUS,50,3);
        fBall4 = new FlyingBall(Color.orange,MAX_BORDER_X-RADIUS,70,4);
        fBall5 = new FlyingBall(Color.MAGENTA,MAX_BORDER_X-RADIUS,90,5);
        fBall1.start();
        fBall2.start();
        fBall3.start();
        fBall4.start();
        fBall5.start();

        repaintByTime();

    }

    private void repaintByTime(){

       while (true) {
           try {
            Thread.sleep(16);

           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           repaint();
       }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        fBall1.draw(g);
        g.setColor(fBall1.getColor());
        g.fillOval(fBall1.getX(),fBall1.getY(),RADIUS,RADIUS);
        g.setColor(fBall2.getColor());
        g.fillOval(fBall2.getX(),fBall2.getY(),RADIUS,RADIUS);
        g.setColor(fBall3.getColor());
        g.fillOval(fBall3.getX(),fBall3.getY(),RADIUS,RADIUS);
        g.setColor(fBall4.getColor());
        g.fillOval(fBall4.getX(),fBall4.getY(),RADIUS,RADIUS);
        g.setColor(fBall5.getColor());
        g.fillOval(fBall5.getX(),fBall5.getY(),RADIUS,RADIUS);
    }
}
