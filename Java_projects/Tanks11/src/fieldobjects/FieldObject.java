package fieldobjects;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import serviceclasses.Drawable;


public class FieldObject implements Drawable{
	protected int x;
	protected int y;
	protected Color color;
	protected BufferedImage image;
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(this.image,this.getX(),this.getY(),64,64, null);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
    
}
