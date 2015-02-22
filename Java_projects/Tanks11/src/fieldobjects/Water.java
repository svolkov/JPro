package fieldobjects;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Water extends FieldObject{

	 public Water(){
		 this.color = new Color(65,105,225);
		 try {
				this.image = ImageIO.read(new File("MyWater1.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			  }
	 }
}
