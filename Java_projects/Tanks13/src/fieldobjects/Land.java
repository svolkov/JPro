package fieldobjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Land extends FieldObject {

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
