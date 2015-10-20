package tanks;

import fieldobjects.*;
import serviceclasses.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class BT7 extends AbstractTank {
	//int maxSpeed = 72;

	public BT7() {
		super();
	}

	public BT7(ActionField af, BattleField battle) {
		super(battle);
		//speed = 5;
	}

	
	public BT7 (BattleField battle, int x, int y, Direction direction) {
		super(battle, x, y, direction);
		//speed = 5;
		this.colorTank=Color.RED;
		this.colorTower=Color.BLACK;
		this.maxSpeed = 12;
		this.action.setPointer(this);
		this.action.setNextAct(null);
		this.action.setActionResult(true);
		
		this.imagesOfTank = new HashMap<Direction,BufferedImage>();
        loadImages();
	}

    private void loadImages(){
        InputStream is = battle.getClassLoader().getResourceAsStream("BrownTank.png");
        try {
            this.imagesOfTank.put(Direction.UP, ImageIO.read(is));
            is.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        is = battle.getClassLoader().getResourceAsStream("BrownTankRight.png");
        try {
            this.imagesOfTank.put(Direction.RIGHT, ImageIO.read(is));
            is.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        is = battle.getClassLoader().getResourceAsStream("BrownTankDown.png");
        try {
            this.imagesOfTank.put(Direction.DOWN, ImageIO.read(is));
            is.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        is = battle.getClassLoader().getResourceAsStream("BrownTankLeft.png");
        try {
            this.imagesOfTank.put(Direction.LEFT, ImageIO.read(is));
            is.close();
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
			case FIRE : this.action.setNextAct(TankActions.MOVE);
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
            if(tmp instanceof Rock){
                break;
            }
            if(tmp instanceof Brick || tmp instanceof Eagle){
                isEnemy = true;
                break;
            }
            if(tmp instanceof Land || tmp instanceof Water){
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
