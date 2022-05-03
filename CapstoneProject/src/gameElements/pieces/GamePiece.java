package gameElements.pieces;
import java.util.ArrayList;

import gameElements.board.*;
import processing.core.PApplet;

public abstract class GamePiece {
	protected int health, damage, x, y;
	
	public ArrayList<Location> calcMoveLocs() {return null;};
	
	public Location getMoveLoc() {return null;}
	
	public void takeDamage(int dmg) {
		health -= dmg;
	}
 
	public void attack(GamePiece enemy) {
		enemy.takeDamage(damage);
	}
	
	public abstract void draw(PApplet marker);
	
	public void die() {}

}
