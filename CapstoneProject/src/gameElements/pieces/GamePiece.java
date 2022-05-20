package gameElements.pieces;
import java.util.ArrayList;

import gameElements.board.*;
import processing.core.PApplet;

/**
 * the GamePiece represents a piece on a chess board
 * @author hfeng636
 *
 */

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
	
	/**
	 * creates a new alive GamePiece in the given row, column, board, and color
	 * @param r the row in which to place the piece
	 * @param c the column in which to place the piece
	 * @param brd the board in which to place the piece
	 * @param wht the color of the piece
	 */
	public GamePiece(int r, int c, Board brd, boolean wht) {
		loc = new Location(r, c);
		board = brd;
		target = null;
		white = wht;
		imgCode = 0;
		dead = false;
	}
	
	/**
	 * causes the gamePiece to act. When acting, the gamepiece will check if its health is 
	 * <= 0, check if its target is untargetable, set a new target if it doesnt already 
	 * have one, get all possible moves, get the best move, move to the best move, 
	 * get all attack targets, damage those targets, and check if its health <= 0
	 */
	public void act() {
		if(!dead) {
			if(health <= 0) {
				die();
			}
			if(target != null && target.isDead()) { target = null;} //target died
			if(target != null && loc.getDistanceFrom(target.getLocation()) >= 10) {target = null;}// target went out of range
			if(target == null) { target = getScan(7);} // get target
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
				die();
			}
		}
		else {
			board.set(null, loc.getRow(), loc.getCol());	
		}
	}
	/** 
	 * constructs a GamePiece
	 */
	public GamePiece() {
		imgCode = 0;
	}
	
	/**
	 *Finds all possible move locations for the GamePiece
	 * @return an ArrayList of Locations indicating all possible move locations
	 */
	public  abstract ArrayList<Location> calcMoveLocs();
	
	/**
	 * chooses a move from all possible moves
	 * @param moves an ArrayList of Locations indicating all possible move locations
	 * @return a Location object indicating the chosen move
	 */
	public abstract Location getMoveLoc(ArrayList<Location> moves);

	/**
	 * moves the GamePiece to the given location
	 * @param newLoc the location to which the GamePiece should move
	 */
	public void moveTo(Location newLoc) {
		board.set(null, loc.getRow(), loc.getCol());
		loc = newLoc;
		board.add(this);
	}
	
	/**
	 * returns a new target GamePiece given a scan radius over which it should scan for the target
	 * @param scanRad the scanRadius to search through to find a target
	 * @return a target, or null if nothing was found, or the current target if this GamePiece already has a target
	 */
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
	
	/**
	 * sets this GamePiece's imgCode to the given imgCode
	 * @param imgCode the imgCode to set current imgCode to
	 */
	public void setImgCode(int imgCode) {
		this.imgCode = imgCode;
	}
	
	/**
	 * returns this GamePiece's imgCode
	 * @return this GamePiece's imgCode
	 */
	public int getImgCode() {
		return imgCode;
	}
	
	/**
	 * returns all GamePieces to attack
	 * @return an ArrayList of GamePieces indicating the GamePieces to attack
	 */
	public abstract ArrayList<GamePiece> getAttackTargets();
	
	/**
	 * deals damage to this GamePiece, reducing its health by the damage amount
	 * @param dmg damage amount
	 */
	public void takeDamage(int dmg) {
		health -= dmg;
	}
 
	/** 
	 * damages the given GamePiece
	 * @param enemy the GamePiece to damage
	 */
	public void attack(GamePiece enemy) {
		enemy.takeDamage(getDamage());
	}
	
	/**
	 * returns this GamePiece's location
	 * @return a Location object indicating this GamePiece's location
	 */
	public Location getLocation() {
		return loc;
	}
	
	/**
	 * draws this GamePiece on the given PApplet surface
	 * @param marker the PApplet surface on which to draw on
	 */
	public abstract void draw(PApplet marker);
	
	/** 
	 * makes this GamePiece "die" by setting the dead field to true and setting this piece's location on the board to null
	 */
	public void die() {
		board.set(null, loc.getRow(), loc.getCol());	
		dead = true;
	}
	
	/**
	 * return this GamePiece's name
	 * @return a String indicating this GamePiece's name
	 */
	public abstract String getName();
	
	/**
	 * return this GamePiece's health
	 * @return an integer indicating this GamePiece's health
	 */
	public int getHealth() {return health;}
	
	/**
	 * return this GamePiece's damage
	 * @return an integer indicating this GamePiece's damage
	 */
	public int getDamage() {return damage;}
	
	/**
	 * Returns a set of all GamePieces (used for card)
	 * @param b board on which the GamePieces reside
	 * @param wht color of the GamePieces
	 * @return an ArrayList of GamePieces
	 */
	public static ArrayList<GamePiece> getSet(Board b, boolean wht) {
		ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
		pieces.add(new Queen(0, 0, b, wht));
		pieces.add(new Knight(0, 0, b, wht));
		pieces.add(new Rook(0, 0, b, wht));
		pieces.add(new Bishop(0, 0, b, wht));
		pieces.add(new Pawn(0, 0, b, wht));
		return pieces;
		
	}
	
	/**
	 * returns whether this GamePiece is dead or not
	 * @return a boolean, true if the GamePiece is dead, false if it is not
	 */
	public boolean isDead() {
		return dead;
	}
	
	/**
	 * returns the energy cost of this GamePiece
	 * @return an integer indicating the GamePiece's energy cost
	 */
	public int getEnergy() {return energy;}
	
	/**
	 * returns the full health of this GamePiece
	 * @return an integer indicating the GamePiece's maximum health
	 */
	public int getFullHealth() {return fullHealth;}

	/**
	 * returns whether this GamePiece is white or not
	 * @return a boolean, true if the GamePiece is white, false if it is not
	 */
	public boolean isWhite() {
		return white;
	}
	
}