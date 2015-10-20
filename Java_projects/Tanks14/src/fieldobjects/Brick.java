package fieldobjects;

import serviceclasses.Destroyable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class Brick extends FieldObject implements Destroyable {

	public Brick(BufferedImage image){
 //       this.pathToImage = pathToImage;
        this.image = image;
			this.color = new Color(178,34,34);
//        File directory = new File("");
//        File file = new File(directory.getAbsolutePath()+"/"+"MyBrick.jpg");
//       System.out.println("Path to image: "+pathToImage);
//        File file = new File(pathToImage+"MyBrick.jpg");
//        System.out.println(file.getAbsolutePath());
//		try {
//			this.image = ImageIO.read(file);//new File("MyBrick.jpg"));
//		} catch (IOException e) {
//			TODO Auto-generated catch block
//			e.printStackTrace();
//		  }
	}
	
	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}
 
	
}
