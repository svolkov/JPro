package tanks;

import fieldobjects.FieldObject;
import serviceclasses.Action;
import serviceclasses.BattleField;
import serviceclasses.Direction;
import serviceclasses.Drawable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;


public abstract class AbstractTank extends FieldObject implements Drawable {
	protected Direction direction;
	protected int speed = 10;
	protected Color colorTank, colorTower;
	int maxSpeed;
	int crew;
	BattleField battle;
	public Action action;

    protected HashMap<Direction,BufferedImage> imagesOfTank;
	
	public AbstractTank() {
	}

	public AbstractTank(BattleField battle) {
		this(battle, 128, 512, Direction.UP);
	}

	public AbstractTank(BattleField battle, int x, int y, Direction direction) {

		this.direction = direction;
		this.x = x;
		this.y = y;
		this.battle = battle;
		this.action = new Action();

	}

	public abstract Action setup();

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void updateX(int x) {
		this.x += x;
	}

	public void updateY(int y) {
		this.y += y;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}


	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int getCrew() {
		return crew;
	}

	public void setCrew(int crew) {
		this.crew = crew;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void draw(Graphics g) {
		// TODO Auto-generated method stub
	
		  g.drawImage(this.imagesOfTank.get(this.getDirection()),this.getX(), this.getY(),64,64, null);
		
	}
}
