package gameElements.pieces;

import java.util.ArrayList;

import core.ImageCodes;
import gameElements.board.Board;
import gameElements.board.Location;
import processing.core.PApplet;

public class Rook extends GamePiece{
	
	public Rook(int r, int c, Board brd, boolean wht) {
		super(r, c, brd, wht);
		health = 20;
		damage = 10;
		maxDist = 3;
		energy = 4;
		range = 3;
		if (wht) {
			super.setImgCode(ImageCodes.WHITE_ROOK);
		} else {
			super.setImgCode(ImageCodes.BLACK_ROOK);
		}
	}

	public void draw(PApplet marker) {}

	@Override
	public ArrayList<Location> calcMoveLocs() {
		ArrayList<Location> locs = new ArrayList<Location>();
		if(isWhite()) {
			for(int j = loc.getCol(); j < loc.getCol()+(int)range; j++) {
				if(!board.inBounds(loc.getRow(), j)) {}
				else {
					if(board.get(loc.getRow(), j) == null) {
						locs.add(new Location(loc.getRow(), j));
					}
				}
			}
		} else {
			for(int j = loc.getCol(); j > loc.getCol()-(int)range; j--) {
				if(!board.inBounds(loc.getRow(), j)) {}
				else {
					if(board.get(loc.getRow(), j) == null) {
						locs.add(new Location(loc.getRow(), j));
					}
				}
			}
		}
			
		return locs;
	}
	
	@Override
	public Location getMoveLoc(ArrayList<Location> moves) {
		if(moves.size() == 0) {return null;}
		int r = (int)(Math.random()*moves.size());
		return moves.get(r);
	}

	@Override
	public ArrayList<GamePiece> getAttackTargets() {
		ArrayList<GamePiece> locs = new ArrayList<GamePiece>();
		if(isWhite()) {
			for(int j = loc.getCol(); j < loc.getCol()+(int)range; j++) {
				if(!board.inBounds(loc.getRow(), j)) {}
				else {
					if(board.get(loc.getRow(), j) != null) {
						locs.add(board.get(j, j));
					}
				}
			}
		} else {
			for(int j = loc.getCol(); j > loc.getCol()-(int)range; j--) {
				if(!board.inBounds(loc.getRow(), j)) {}
				else {
					if(board.get(loc.getRow(), j) != null) {
						locs.add(board.get(j, j));
					}
				}
			}
		}
		
		
		return locs;
	}
	
	@Override
	public String getName() {
		return "Rook";
	}
 	
}
