package serviceclasses;

import tanks.AbstractTank;

import java.awt.*;


public class Bullet implements Drawable, Destroyable {
	private int x;
	private int y;
	private int speed = 5;
	private Direction direction;
	private AbstractTank tank;



    public Bullet(int x, int y, Direction direction, AbstractTank tank){
		   this.x = calcStartX(x,direction);
		   this.y = calcStartY(y,direction);
		   this.direction = direction;
           this.tank = tank;

		}
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
		
	public int getSpeed() {
		return speed;
	}
		
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public void updateX(int x){
		this.x += x;
	}
	public void updateY(int y){
		this.y += y;
	}
	public void destroy(){
		x = -100;
		y = -100;
        synchronized (getTank()){
            getTank().notify();
        }
     //   Thread.currentThread().interrupt(); //???
	}
	
	public void draw(Graphics g){
		g.setColor(new Color(255, 249, 2));
		//g.fillRect(this.getX(), this.getY(), 14, 14);
        g.fillOval(this.getX(), this.getY(), 14, 14);
	}

    public AbstractTank getTank() {
        return tank;
    }

    public void setTank(AbstractTank tank) {
        this.tank = tank;
    }

    private int calcStartX(int x, Direction direction){
        int result = 0;

        switch (direction){
            case UP : result = x + 25;
                      break;
            case DOWN: result = x + 25;
                       break;
            case LEFT : result = x;
                break;
            case RIGHT: result = x + 50;
                break;
        }
        return result;
    }

    private int calcStartY(int y, Direction direction){
        int result = 0;

        switch (direction){
            case UP : result = y;
                break;
            case DOWN: result = y + 50;
                break;
            case LEFT : result = y + 25;
                break;
            case RIGHT: result = y + 25;
                break;
        }
        return result;
    }
}
