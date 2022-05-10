package gameElements.pieces;

import java.util.ArrayList;

import core.ImageCodes;
import gameElements.board.Board;
import gameElements.board.Location;
import processing.core.PApplet;

public class Bishop extends GamePiece{
	
	public Bishop(int r, int c, Board brd, boolean wht) {
		super(r, c, brd, wht);
		health = 10;
		damage = 10;
		maxDist = 3;
		range = 3;
		if (wht) {
			super.setImgCode(ImageCodes.WHITE_BISHOP);
		} else {
			super.setImgCode(ImageCodes.BLACK_BISHOP);
		}
	}
	
	public void draw(PApplet marker) {}

	@Override
	public ArrayList<Location> calcMoveLocs() {
		// TODO Auto-generated method stub
		return null;
	}

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
	
	public String getName() {
		return "Bishop";
	}

}
