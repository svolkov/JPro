import javax.swing.*;
import java.awt.*;

/**
 * Created by syv on 11.03.15.
 */
public class SpaceField extends JPanel {
    private Ship ship;
    private Gate gate;
    private JFrame f;

    public SpaceField(){
        f = new JFrame("SpaceGates");
        f.setLocation(100, 100);
        f.setMinimumSize(new Dimension(500, 400));
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.getContentPane().add(this);
        f.setVisible(true);
    }

    public void fly(){
      ship = new Ship();
      gate = new Gate();

      startGateService();
      startShipMission();

      refreshDisplay();
    }

    private void startGateService(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (gate) {
                        try {
                            System.out.println("Gate is waiting");
                            gate.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Gate started to open");
                    gate.open();
                    System.out.println("Gate is open");

                    synchronized (ship) {
                        ship.notify();
                    }

                    synchronized (gate) {
                        try {
                            System.out.println("Gate is waiting");
                            gate.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Gate started to close");
                    gate.close();
                    System.out.println("Gate is closed");
                }
            }
        }).start();
    }

    private void startShipMission(){
        new Thread(new Runnable() {
            @Override
            public void run() {
              while (true) {
                  ship.setX(0);
                  ship.setGateX(260);
                  ship.setCloselyToGate(false);

                  while (!ship.isCloselyToGate()) {
                      ship.go();
                  }
                  System.out.println("Ship ask gate to open ");
                  synchronized (gate) {
                      gate.notify();
                  }
                  System.out.println("Ship is waiting ");
                  synchronized (ship) {
                      try {
                          ship.wait();
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }
                  }

                  for (int i = 0; i < 70; i++) {
                      ship.go();
                  }
                  System.out.println("Ship ask gate to close ");
                  synchronized (gate) {
                      gate.notify();
                  }

                  System.out.println("Ship is going forward ");

                  while (ship.getX() < f.getWidth())
                      ship.go();

              }
            }
        }).start();

    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ship.draw(g);
        gate.draw(g);
    }

    private void refreshDisplay(){

        while (true) {
            try {
                Thread.sleep(1000/75);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }
}
