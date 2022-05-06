package gameElements.pieces;
import java.util.ArrayList;

import gameElements.board.*;
import processing.core.PApplet;

public abstract class GamePiece {
	protected int health, damage;
	protected Location loc;
	protected GamePiece target;
	protected Board board;
	
	public GamePiece(int r, int c) {
		loc = new Location(r, c);
	}
	
	public void act() {
		ArrayList<Location> moveLocs = calcMoveLocs();
		Location optimal = getMoveLoc(moveLocs);
		moveTo(optimal);
		ArrayList<GamePiece> toAttack = getAttackTargets();
		for(GamePiece gp : toAttack) {attack(gp);}
		if(health <= 0) {die();}
	}
	
	public GamePiece() {
		
	}
	
	public  abstract ArrayList<Location> calcMoveLocs();
	
	public abstract Location getMoveLoc(ArrayList<Location> moves);

	public void moveTo(Location newLoc) {
		loc.setRow(newLoc.getRow());
		loc.setCol(newLoc.getCol());
	}
	
	public GamePiece getScan(int scanLength) {return null;}
	
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
	
	public Location getLoc() {return loc;}

}
