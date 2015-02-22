package tanks;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import fieldobjects.FieldObject;
import fieldobjects.Rock;
import serviceclasses.Action;
import serviceclasses.BattleField;
import serviceclasses.Direction;
import serviceclasses.TankActions;


public class T34 extends AbstractTank{
 // int maxSpeed = 10;
	
	public T34() {
		super();
	}
    public T34(BattleField battle) {
		super(battle,128,512,Direction.UP);
	}
    public T34(BattleField battle, int x, int y, Direction direction) {
		super(battle, x, y, direction);
		this.colorTank=Color.YELLOW;
		this.colorTower=Color.BLUE;
		this.action.setPointer(this);
		this.action.setNextAct(null);
		this.action.setActionResult(true);
		this.maxSpeed = 5;
		
		this.battle.setFieldObject(y/64, x/64, this);
		this.imagesOfTank = new HashMap<Direction,BufferedImage>();
		
		try {
			this.imagesOfTank.put(Direction.UP, ImageIO.read(new File("RedTank.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			this.imagesOfTank.put(Direction.RIGHT, ImageIO.read(new File("RedTankRight.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			this.imagesOfTank.put(Direction.DOWN, ImageIO.read(new File("RedTankDown.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.imagesOfTank.put(Direction.LEFT, ImageIO.read(new File("RedTankLeft.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	
	public Action setup() {
			// TODO Auto-generated method stub
				
		if (this.action.isActionResult()){
			  this.action.setAttemptCount(0);
			  if (enemyDirect()){
				  this.action.setNextAct(TankActions.FIRE);
			  }else{
			   	  this.action.setNextAct(TankActions.MOVE);
			  
			 }
			}else{
				switch(this.action.getNextAct()){
				case FIRE : if (enemyDirect()){
				                   this.action.setNextAct(TankActions.FIRE);
			                     }else{
			                        this.action.setNextAct(TankActions.MOVE);
			                      } 
					        //this.action.setNextAct(TankActions.MOVE);
				            break;
				case  TURN_LEFT: if (enemyDirect()){
				                   this.action.setNextAct(TankActions.FIRE);
			                     }else{
			                        this.action.setNextAct(TankActions.MOVE);
			                      } 
				                 break; 
				case TURN_RIGHT: if (enemyDirect()){
				                    this.action.setNextAct(TankActions.FIRE);
			                     }else{
			                         this.action.setNextAct(TankActions.MOVE);
			                      }  
				                 break;
				case      MOVE : if(this.action.getPrevTurnAct()==TankActions.TURN_RIGHT &&
						             this.action.getAttemptCount()==0){
										this.action.setNextAct(TankActions.TURN_LEFT);
										this.action.setPrevTurnAct(TankActions.TURN_LEFT);
										this.action.setAttemptCount(this.action.getAttemptCount()+1);
				                 }else{
				     				if (this.action.getAttemptCount()==0){
				    					this.action.setNextAct(TankActions.TURN_RIGHT);
				    			        this.action.setPrevTurnAct(TankActions.TURN_RIGHT);
				    			         this.action.setAttemptCount(this.action.getAttemptCount()+1);
				    			    }else{
				    			    	this.action.setNextAct(TankActions.TURN_LEFT);
				    				    this.action.setPrevTurnAct(TankActions.TURN_LEFT);
				    				    this.action.setAttemptCount(this.action.getAttemptCount()+1);
				    			    }
				                  }
				}
			  }
	      return this.action;
		}
		
	    private boolean enemyDirect(){
	    	boolean enemyFound = false;
	    	int V = this.y/64;
	    	int H = this.x/64;
	    	      	
	    	switch (this.direction){
	    	case UP : enemyFound = checkLine(V,-1,H,0);
	    	         break;
	    	case DOWN : enemyFound = checkLine(V,1,H,0);
	    	         break; 
	    	case LEFT : enemyFound = checkLine(V,0,H,-1);
	    	         break; 
	    	case RIGHT : enemyFound = checkLine(V,0,H,1);
	    	         break;         
	    	}
	    	return enemyFound; 
	    }
	    
	    private boolean checkLine(int V, int stepV, int H, int stepH){
	    	boolean isEnemy = false;
	    	
	    	FieldObject tmp = null;
	    	
	    	   do{ 
	    	    tmp = this.battle.getNextFieldObject(V, H, this.getDirection());
	    		if(tmp instanceof Rock){
	    			break;
	    		}
	    	    if ((tmp instanceof Tiger)||(tmp instanceof BT7)){
	    			isEnemy = true;
	    			break;
	    		}
	    		V+=stepV;
	    		H+=stepH;
	    	   }
	    	   while (tmp != null);
	    	
	    	return isEnemy;
	    }
	    
/*	    private boolean isFarFromEagle(){
	    	
	    	if(this.getY()<=(512-64*2)){
	    		return true;
	    	}else{
	    		return false;
	    	}
	    }*/
  }
