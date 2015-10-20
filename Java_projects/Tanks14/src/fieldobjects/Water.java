package fieldobjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Water extends FieldObject {

	 public Water(BufferedImage image){
   //      this.pathToImage = pathToImage;
         this.image = image;
		 this.color = new Color(65,105,225);
//         File file = new File(pathToImage+"MyWater1.png");
//         try {
//				this.image = ImageIO.read(file);
//			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
//			  }
	 }
}
