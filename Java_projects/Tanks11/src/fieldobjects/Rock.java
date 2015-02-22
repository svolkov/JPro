package fieldobjects;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import serviceclasses.Destroyable;


public class Rock extends FieldObject implements Destroyable{

	boolean destroyableByTiger = true; 
	
	public Rock(){
		this.color = new Color(128,128,128);
		try {
			this.image = ImageIO.read(new File("MyStones.jpg"));
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
