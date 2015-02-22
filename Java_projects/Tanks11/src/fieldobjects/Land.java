package fieldobjects;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Land extends FieldObject{

	public Land() {
		 this.color = new Color(102,255,102);
		 try {
				this.image = ImageIO.read(new File("MyGrass1.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			  }
	}
	
	public Land(int x,int y) {
		 this.color = new Color(102,255,102);
		 this.x = x;
		 this.y = y;
		  try {
				this.image = ImageIO.read(new File("MyGrass1.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			  }
	}
 
}
