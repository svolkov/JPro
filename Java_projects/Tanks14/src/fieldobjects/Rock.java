package fieldobjects;

import serviceclasses.Destroyable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Rock extends FieldObject implements Destroyable {

	boolean destroyableByTiger = true; 
	
	public Rock(BufferedImage image){
    //    this.pathToImage = pathToImage;
        this.image = image;
		this.color = new Color(128,128,128);
//        File file = new File(pathToImage+"MyStones.jpg");
//        try {
//			this.image = ImageIO.read(file);
//		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		  }
	}
	
	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
