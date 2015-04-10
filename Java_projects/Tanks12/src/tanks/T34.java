package tanks;

import fieldobjects.*;
import serviceclasses.Action;
import serviceclasses.BattleField;
import serviceclasses.Direction;
import serviceclasses.TankActions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class T34 extends AbstractTank {
 // int maxSpeed = 10;
	
	public T34() {
		super();
	}
    public T34(BattleField battle) {
		super(battle,128,512, Direction.UP);
	}
    public T34(BattleField battle, int x, int y, Direction direction) {
		super(battle, x, y, direction);
		this.colorTank=Color.YELLOW;
		this.colorTower=Color.BLUE;
		this.action.setPointer(this);
		this.action.setNextAct(null);
		this.action.setActionResult(true);
		this.maxSpeed = 15;//5;
		
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
				case      MOVE : if(this.action.getPrevTurnAct()== TankActions.TURN_RIGHT &&
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
	    		if((tmp instanceof Rock) || (tmp instanceof Brick)){
	    			break;
	    		}
	    	    if ((tmp instanceof Land)||(tmp instanceof Water)){
	    		  if(isEnemyInQuadrant(V, H, this.getDirection())) {
                      isEnemy = true;
                      break;
                  }
	    		}
	    		V+=stepV;
	    		H+=stepH;
	    	   }
	    	   while (tmp != null);
	    	
	    	return isEnemy;
	    }

     private boolean isEnemyInQuadrant(int v, int h, Direction direction){
        boolean result = false;
        int enemyX, enemyY;

        switch (direction){
            case UP    : v--;
                         break;
            case DOWN  : v++;
                         break;
            case LEFT  : h--;
                         break;
            case RIGHT : h++;
                         break;
        }
        v = v*64;
        h = h * 64;
        enemyX = battle.getAggressorX();
        enemyY = battle.getAggressorY();

        if((enemyX > (h - 50)) && (enemyX < (h + 50)) && (enemyY > (v - 50)) && (enemyY < (v + 50))) {
            result = true;
        }
        return result;
    }

  }
