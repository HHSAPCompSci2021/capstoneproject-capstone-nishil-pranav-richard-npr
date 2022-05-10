package gameElements.pieces;

import java.util.ArrayList;

import core.ImageCodes;
import gameElements.board.Board;
import gameElements.board.Location;
import processing.core.PApplet;

public class Rook extends GamePiece{
	
	public Rook(int r, int c, Board brd, boolean wht) {
		super(r, c, brd, wht);
		health = 10;
		damage = 10;
		maxDist = 3;
		range = 3;
		if (wht) {
			super.setImgCode(ImageCodes.WHITE_ROOK);
		} else {
			super.setImgCode(ImageCodes.BLACK_ROOK);
		}
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
	
	public String getName() {
		return "Rook";
	}
 	
}
