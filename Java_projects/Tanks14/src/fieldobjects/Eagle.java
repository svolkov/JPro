package fieldobjects;

import serviceclasses.Destroyable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Eagle extends FieldObject implements Destroyable {

	public Eagle(BufferedImage image){
        //this.pathToImage = pathToImage;
        this.image = image;
        this.color = new Color(255,255,255);
//        File file = new File(pathToImage+"MyEagle.jpg");
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
