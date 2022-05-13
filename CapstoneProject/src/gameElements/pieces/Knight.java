package gameElements.pieces;

import java.util.ArrayList;

import core.ImageCodes;
import gameElements.board.Board;
import gameElements.board.Location;
import processing.core.PApplet;

public class Knight extends GamePiece {
	
	public Knight(int r, int c, Board brd, boolean wht) {
		super(r, c, brd, wht);
		fullHealth = 15;
		health = 10;
		damage = 6;
		maxDist = 2;
		energy = 2;
		range = 2;
		if (wht) {
			super.setImgCode(ImageCodes.WHITE_KNIGHT);
		} else {
			super.setImgCode(ImageCodes.BLACK_KNIGHT);
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
		return "Knight";
	}
}
