package gameElements.pieces;

import java.util.ArrayList;

import gameElements.board.Board;
import gameElements.board.Location;
import processing.core.PApplet;

public class Queen extends GamePiece{
	
	public Queen(int r, int c, Board brd) {
		super(r, c, brd);
	}
	
	
	public void draw(PApplet marker) {}


	@Override
	public Location getMoveLoc(ArrayList<Location> moves) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<GamePiece> getAttackTargets() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Location> calcMoveLocs() {
		// TODO Auto-generated method stub
		return null;
	}

}
