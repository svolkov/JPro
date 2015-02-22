package fieldobjects;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import serviceclasses.Destroyable;


public class Eagle extends FieldObject implements Destroyable{

	public Eagle(){
		this.color = new Color(255,255,255);
		try {
			this.image = ImageIO.read(new File("MyEagle.jpg"));
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
