package fieldobjects;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import serviceclasses.Destroyable;
import serviceclasses.Direction;


public class Brick extends FieldObject implements Destroyable{

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
