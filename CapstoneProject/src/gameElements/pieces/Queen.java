package gameElements.pieces;

import processing.core.PApplet;

public class Queen extends GamePiece{
	
	public Queen(int r, int c) {
		super(r, c);
		target = null;
		health = 10;
		damage = 10;
	}
	
	
	public void draw(PApplet marker) {}

}
