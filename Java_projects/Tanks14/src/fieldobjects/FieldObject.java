package fieldobjects;

import serviceclasses.Drawable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;


public class FieldObject implements Drawable, Serializable{
	protected int x;
	protected int y;
	protected Color color;
	protected transient BufferedImage image;
    protected String pathToImage;
	
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

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
