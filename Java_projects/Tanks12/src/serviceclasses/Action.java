package serviceclasses;
import tanks.AbstractTank;

import java.io.Serializable;


public class Action {
	private AbstractTank pointer;
	private TankActions nextAct;
	private TankActions prevTurnAct = TankActions.TURN_LEFT;
	private boolean actionResult;
	private int attemptCount;
	
	
	public int getAttemptCount() {
		return attemptCount;
	}
	public void setAttemptCount(int attemptCount) {
		this.attemptCount = attemptCount;
	}
	public TankActions getPrevTurnAct() {
		return prevTurnAct;
	}
	public void setPrevTurnAct(TankActions prevTurnAct) {
		this.prevTurnAct = prevTurnAct;
	}
	public boolean isActionResult() {
		return actionResult;
	}
	public void setActionResult(boolean actionResult) {
		this.actionResult = actionResult;
	}
	public AbstractTank getPointer() {
		return pointer;
	}
	public void setPointer(AbstractTank pointer) {
		this.pointer = pointer;
	}
	public TankActions getNextAct() {
		return nextAct;
	}
	public void setNextAct(TankActions nextAct) {
		this.nextAct = nextAct;
	}
	
}
