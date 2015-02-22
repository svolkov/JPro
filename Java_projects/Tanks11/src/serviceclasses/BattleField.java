package serviceclasses;

import fieldobjects.Brick;
import fieldobjects.Eagle;
import fieldobjects.FieldObject;
import fieldobjects.Land;
import fieldobjects.Rock;
import fieldobjects.Water;

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
	final int BF_WIDTH = 576;
	final int BF_HEIGHT = 576;
	
	
	public BattleField(){
		this.fieldOfObjects = new FieldObject[9][9];
		initFieldOfObjects();
		}
	
	public BattleField(String[][] battleField){
		this.battleField = battleField;
	   }
	
	private void initFieldOfObjects(){
		
		for(int v = 0; v < 9; v++){
			//fieldOfObjects[v] = new FieldObject[9];
			for(int h = 0; h < 9; h++){
				switch(battleField[v][h]){
				case " " : fieldOfObjects[v][h] = new Land();
				           break;
				case "B" : fieldOfObjects[v][h] = new Brick();
				           break; 
				case "E" : fieldOfObjects[v][h] = new Eagle();
				           break;  
				case "R" : fieldOfObjects[v][h] = new Rock();
				           break;    
				case "W" : fieldOfObjects[v][h] = new Water();
				           break;           
				}
				fieldOfObjects[v][h].setX(h*64);
				fieldOfObjects[v][h].setY(v*64);
			}
		}
	}
	
	public FieldObject getFieldObject(int v, int h){
		return fieldOfObjects[v][h];
	}
	
	public void setFieldObject(int v, int h, FieldObject o){
		fieldOfObjects[v][h] = o;
	}
	
	public FieldObject getNextFieldObject(int v, int h,Direction direction){
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
}
