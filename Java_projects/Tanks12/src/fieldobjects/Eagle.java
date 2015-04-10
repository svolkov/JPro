package fieldobjects;

import serviceclasses.Destroyable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Eagle extends FieldObject implements Destroyable {

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
