package gameElements.pieces;
import java.util.ArrayList;

import gameElements.board.*;
import processing.core.PApplet;

public abstract class GamePiece {
	protected int fullHealth, health, damage, energy;
	protected Location loc;
	protected GamePiece target;
	protected Board board;
	protected int maxDist;
	protected boolean white;
	protected int[] moveR = {1, -1, 0, 0, 1, -1, 1, -1};
	protected int[] moveC = {0, 0, 1, -1, 1, -1, -1, 1};
	protected double range;
	protected int imgCode;
	protected boolean dead;
	
	public GamePiece(int r, int c, Board brd, boolean wht) {
		loc = new Location(r, c);
		board = brd;
		target = null;
		white = wht;
		imgCode = 0;
		dead = false;
	}
	
	public void act() {
		if(!dead) {
			if(health <= 0) {
				die();
			}
			if(target != null && target.isDead()) { target = null;} //target died
			if(target != null && loc.getDistanceFrom(target.getLocation()) >= 7) {target = null;}// target went out of range
			if(target == null) { target = getScan(5);} // get target
			if(loc == null) {return;}
			ArrayList<Location> moveLocs = calcMoveLocs();
			Location optimal = getMoveLoc(moveLocs);
			moveTo(optimal);
			ArrayList<GamePiece> toAttack = getAttackTargets();
			if(toAttack != null) {
				for(GamePiece gp : toAttack) {
					if(gp.isWhite() != white) {
						attack(gp);
					}
				}
			}
			if(health <= 0) {
				//die();
			}
		}
		else {
			board.set(null, loc.getRow(), loc.getCol());	
		}
	}
	
	public GamePiece() {
		imgCode = 0;
	}
	
	public  abstract ArrayList<Location> calcMoveLocs();
	
	public abstract Location getMoveLoc(ArrayList<Location> moves);

	public void moveTo(Location newLoc) {
		board.set(null, loc.getRow(), loc.getCol());
		loc = newLoc;
		board.add(this);
	}
	
	public GamePiece getScan(int scanRad) {
		if(target == null) {
			GamePiece toScan = null;
			int row = loc.getRow(), col = loc.getCol();
			for(int r = row-scanRad; r <= row + scanRad; r++ ) {
				for(int c = col-scanRad; c <= col+scanRad; c++) {
					if(board.inBounds(r, c)) {
						if(r==row && c == col) {}
						else {
							if(board.get(r, c) != null && board.get(r, c).isWhite() != white) {
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
		else {
			return target;
		}
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
		if(health <= 0) {
			//die();
		}
	}
 
	public void attack(GamePiece enemy) {
		enemy.takeDamage(damage);
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public abstract void draw(PApplet marker);
	
	public void die() {
		board.set(null, loc.getRow(), loc.getCol());	
		dead = true;
	}
	
	public abstract String getName();
	public int getHealth() {return health;}
	public int getDamage() {return damage;}
	
	public static ArrayList<GamePiece> getSet(Board b, boolean wht) {
		ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
		pieces.add(new Queen(0, 0, b, wht));
		pieces.add(new Knight(0, 0, b, wht));
		pieces.add(new Rook(0, 0, b, wht));
		pieces.add(new Bishop(0, 0, b, wht));
		pieces.add(new Pawn(0, 0, b, wht));
		return pieces;
		
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public int getEnergy() {return energy;}
	
	public int getFullHealth() {return fullHealth;}

	public boolean isWhite() {
		return white;
	}
	
}