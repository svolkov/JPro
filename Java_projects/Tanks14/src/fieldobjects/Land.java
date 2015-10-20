package fieldobjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Land extends FieldObject {

	public Land(BufferedImage image){
//        this.pathToImage = pathToImage;
        this.image = image;
		this.color = new Color(102,255,102);
//        File file = new File(pathToImage+"MyGrass1.jpg");
//		 try {
//				this.image = ImageIO.read(file);
//			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
//			  }
	}
	
	public Land(int x,int y, BufferedImage image) {
        this.image = image;
		this.color = new Color(102,255,102);
		this.x = x;
		this.y = y;
//        File file = new File(pathToImage+"MyGrass1.jpg");
//        try {
//            this.image = ImageIO.read(file);
//        } catch (IOException e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

//		    try {
//				this.image = ImageIO.read(new File("MyGrass1.jpg"));
//			} catch (IOException e) {
//				TODO Auto-generated catch block
//				e.printStackTrace();
//			  }
	}
 
}
