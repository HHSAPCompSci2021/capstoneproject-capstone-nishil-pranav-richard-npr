package gameElements.pieces;
import java.util.ArrayList;

import gameElements.board.*;
import processing.core.PApplet;

public abstract class GamePiece {
	protected int health, damage, imgIndex;
	
	public ArrayList<Location> calcMoveLocs() {return null;}
	
	public Location getMoveLoc() {return null;}
	
 
	public void attack(GamePiece enemy) {
		enemy.health -= damage;
	}
	
	public abstract void draw(PApplet marker);
	
	public void die() {}

}
