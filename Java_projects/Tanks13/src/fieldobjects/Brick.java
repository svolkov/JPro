package fieldobjects;

import serviceclasses.Destroyable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Brick extends FieldObject implements Destroyable {

	public Brick(){
		this.color = new Color(178,34,34); 
		try {
			this.image = ImageIO.read(new File("MyBrick.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }	
	}
	
	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}
 
	
}
