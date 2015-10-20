package serviceclasses;

import fieldobjects.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BattleField {
	private FieldObject[][] fieldOfObjects;
	private String[][] battleField = {
			{ "B", "B", " ", "W", "W", "R", " ", "R", " " },
			{ " ", " ", " ", " ", "W", "R", " ", " ", "W" },
			{ " ", " ", "R", " ", "W", " ", " ", "B", "W" },
			{ "W", " ", "R", " ", " ", " ", "B", "B", " " },
			{ "W", " ", " ", " ", "B", " ", " ", "B", " " },
			{ "W", "W", " ", "B", "R", "B", " ", "B", " " },
			{ " ", "B", " ", " ", " ", " ", " ", " ", " " },
			{ "R", " ", " ", "B", "B", "B", " ", "R", " " },
			{ "R", " ", " ", " ", "E", " ", " ", "R", "R" }
			};
    private String[][] battleFieldSimple = {
            { "B", "B", " ", "B", "B", "B", " ", "B", " " },
            { " ", " ", " ", " ", "B", "B", " ", " ", "B" },
            { " ", " ", "B", " ", "B", " ", " ", "B", "B" },
            { "B", " ", "B", " ", " ", " ", "B", "B", " " },
            { "B", " ", " ", " ", "B", " ", " ", "B", " " },
            { "B", "B", " ", "B", "B", "B", " ", "B", " " },
            { " ", "B", " ", " ", " ", " ", " ", " ", " " },
            { "B", " ", " ", "B", "B", "B", " ", "B", " " },
            { " ", " ", " ", " ", "B", " ", " ", "B", "B" }
    };
	final int BF_WIDTH = 576;
	final int BF_HEIGHT = 576;

    private AtomicInteger defenderX;
    private AtomicInteger defenderY;
    private AtomicInteger aggressorX;
    private AtomicInteger aggressorY;
	private HashMap <Class,BufferedImage> imageStore;

	public BattleField(){
        defenderX = new AtomicInteger(0);
        defenderY = new AtomicInteger(0);
        aggressorX = new AtomicInteger(0);
        aggressorY = new AtomicInteger(0);
        imageStore = new HashMap<>();
        loadImageStore();
		this.fieldOfObjects = new FieldObject[9][9];
		initFieldOfObjects();
		}
	
	public BattleField(String[][] battleField){
		this.battleField = battleField;
	   }
	
	private void initFieldOfObjects(){
		
		for(int v = 0; v < 9; v++){
			for(int h = 0; h < 9; h++){
				switch(battleField[v][h]){
				case " " : fieldOfObjects[v][h] = new Land(imageStore.get(Land.class));
				           break;
				case "B" : fieldOfObjects[v][h] = new Brick(imageStore.get(Brick.class));
				           break; 
				case "E" : fieldOfObjects[v][h] = new Eagle(imageStore.get(Eagle.class));
				           break;  
				case "R" : fieldOfObjects[v][h] = new Rock(imageStore.get(Rock.class));
				           break;    
				case "W" : fieldOfObjects[v][h] = new Water(imageStore.get(Water.class));
				           break;
				}
				fieldOfObjects[v][h].setX(h*64);
				fieldOfObjects[v][h].setY(v*64);
			}
		}
	}
	
	public synchronized FieldObject getFieldObject(int v, int h){
		return fieldOfObjects[v][h];
	}
	
	public synchronized void setFieldObject(int v, int h, FieldObject o){
		fieldOfObjects[v][h] = o;
	}
	
	public synchronized FieldObject getNextFieldObject(int v, int h, Direction direction){
		FieldObject result=null;
		
		switch (direction){
		  case UP : if(v>0){
			        result=getFieldObject(v-1,h);
		            }
		            break;
		  case DOWN : if(v<8){
			          result=getFieldObject(v+1,h);
		              }
		              break;
		  case LEFT : if(h>0){
			          result=getFieldObject(v,h-1);
		              }
		  			  break;
		  case RIGHT : if(h<8){  
			           result=getFieldObject(v,h+1);
		               } 
		              break;  
		 }
	  return result;
	}
	
	public String scanQuadrant(int v, int h){
		return battleField[v][h];
	}
	
	public String scanQuadrantNext(int v, int h, Direction direction){
	  String result=null;	
	  	 
		switch (direction){
		  case UP : if(v>0){
			        result=scanQuadrant(v-1,h);
		            }
		            break;
		  case DOWN : if(v<8){
			          result=scanQuadrant(v+1,h);
		              }
		              break;
		  case LEFT : if(h>0){
			          result=scanQuadrant(v,h-1);
		              }
		  			  break;
		  case RIGHT : if(h<8){  
			           result=scanQuadrant(v,h+1);
		               } 
		              break;  
		 }
	  return result;
	}
	
	public void updateQuadrant(int v, int h, String s){
		battleField[v][h]=s;
	}
	public int getDimentionY(){
		return battleField.length;
	}
	public int getDimentionX(){
		return battleField[0].length;
	}

    public int getDefenderX() {
        return defenderX.get();
    }

    public void setDefenderX(int x) {
        defenderX.set(x);
    }

    public int getDefenderY() {
        return defenderY.get();
    }

    public void setDefenderY(int y) {
        defenderY.set(y);
    }

    public int getAggressorX() {
        return aggressorX.get();
    }

    public void setAggressorX(int x) {
        aggressorX.set(x);
    }

    public int getAggressorY() {
        return aggressorY.get();
    }

    public void setAggressorY(int y) {
        aggressorY.set(y);
    }

    public void reloadFieldForCleaning(){
        battleField = battleFieldSimple;
        initFieldOfObjects();
    }
    public ClassLoader getClassLoader(){
      return this.getClass().getClassLoader();
    }

    private void loadImageStore(){

//        File file = new File(pathToImage+"MyBrick.jpg");
//        System.out.println(pathToImage+"MyBrick.jpg");
//        InputStream is = this.getClass().getResourceAsStream("Tanks14/images/MyBrick.jpg");
//        ClassLoader classLoader = this.getClass().getClassLoader();
        ClassLoader classLoader = getClassLoader();
        InputStream is = classLoader.getResourceAsStream("MyBrick.jpg");

        try {
            imageStore.put(Brick.class,ImageIO.read(is));//file));
            is.close();
        } catch (IOException e) {
//			TODO Auto-generated catch block
            e.printStackTrace();
        }
//        file = new File(pathToImage+"MyEagle.jpg");
        is = classLoader.getResourceAsStream("MyEagle.jpg");

        try {
            imageStore.put(Eagle.class,ImageIO.read(is));//file));
            is.close();
        } catch (IOException e) {
//			TODO Auto-generated catch block
            e.printStackTrace();
        }
//        file = new File(pathToImage+"MyGrass1.jpg");
        is = classLoader.getResourceAsStream("MyGrass1.jpg");
        try {
            imageStore.put(Land.class,ImageIO.read(is));//file));
            is.close();
        } catch (IOException e) {
//			TODO Auto-generated catch block
            e.printStackTrace();
        }
//        file = new File(pathToImage+"MyStones.jpg");
        is = classLoader.getResourceAsStream("MyStones.jpg");
        try {
            imageStore.put(Rock.class,ImageIO.read(is));//file));
            is.close();
        } catch (IOException e) {
//			TODO Auto-generated catch block
            e.printStackTrace();
        }
 //       file = new File(pathToImage+"MyWater1.png");
        is = classLoader.getResourceAsStream("MyWater1.png");
        try {
            imageStore.put(Water.class,ImageIO.read(is));//file));
            is.close();
        } catch (IOException e) {
//			TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public BufferedImage getImage(Class c){
        return imageStore.get(c);
    }
}
