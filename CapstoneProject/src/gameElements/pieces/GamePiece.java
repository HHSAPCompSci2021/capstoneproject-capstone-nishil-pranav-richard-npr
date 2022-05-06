package gameElements.pieces;
import java.util.ArrayList;

import gameElements.board.*;
import processing.core.PApplet;

public abstract class GamePiece {
	protected int health, damage;
	protected Location loc;
	protected GamePiece target;
	protected Board board;
	protected int maxDist;
	protected boolean white;
	protected int[] moveR = {1, -1, 0, 0, 1, -1, 1, -1};
	protected int[] moveC = {0, 0, 1, -1, 1, -1, -1, 1};
	protected double range;
	protected int imgCode;
			
	public GamePiece(int r, int c, Board brd, boolean wht) {
		loc = new Location(r, c);
		board = brd;
		target = null;
		white = wht;
		imgCode = 0;
	}
	
	public void act() {
		if(target == null) { target = getScan(3);}
		ArrayList<Location> moveLocs = calcMoveLocs();
		Location optimal = getMoveLoc(moveLocs);
		moveTo(optimal);
		ArrayList<GamePiece> toAttack = getAttackTargets();
		for(GamePiece gp : toAttack) {attack(gp);}
		if(health <= 0) {die();}
	}
	
	public GamePiece() {
		imgCode = 0;
	}
	
	public  abstract ArrayList<Location> calcMoveLocs();
	
	public abstract Location getMoveLoc(ArrayList<Location> moves);

	public void moveTo(Location newLoc) {
		board.set(null, loc.getRow(), loc.getCol());
		loc.setRow(newLoc.getRow());
		loc.setCol(newLoc.getCol());
		board.set(this, loc.getRow(), loc.getCol());
		
	}
	
	public GamePiece getScan(int scanRad) {
		GamePiece toScan = null;
		int row = loc.getRow(), col = loc.getCol();
		for(int r = row-scanRad; r <= row + scanRad; r++ ) {
			for(int c = col-scanRad; c <= col+scanRad; c++) {
				if(board.inBounds(r, c)) {
					if(r==row && c == col) {}
					else {
						if(board.get(r, c) != null) {
							if(toScan == null) {
								toScan = board.get(r, c);
							}
							else {
								if(toScan.getLocation().getDistanceFrom(loc) > board.get(r, c).getLocation().getDistanceFrom(loc)) {
									toScan = board.get(r, c);
								}
							}
						}
					}
				}
			}
		}
		return toScan;
	}
	
	public void setImgCode(int imgCode) {
		this.imgCode = imgCode;
	}
	
	public int getImgCode() {
		return imgCode;
	}
	
	public abstract ArrayList<GamePiece> getAttackTargets();
	
	public void takeDamage(int dmg) {
		health -= dmg;
	}
 
	public void attack(GamePiece enemy) {
		enemy.takeDamage(damage);
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public abstract void draw(PApplet marker);
	
	public void die() {}
	

}