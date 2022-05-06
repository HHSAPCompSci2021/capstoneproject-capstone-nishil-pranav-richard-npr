package gameElements.pieces;

import java.util.ArrayList;

import core.ImageCodes;
import gameElements.board.Board;
import gameElements.board.Location;
import processing.core.PApplet;

public class Pawn extends GamePiece{
	
	public Pawn(int r, int c, Board brd, boolean wht) {
		super(r, c, brd, wht);
		health = 10;
		damage = 10;
		maxDist = 3;
		range = 3;
		if (wht) {
			super.setImgCode(ImageCodes.WHITE_PAWN);
		} else {
			super.setImgCode(ImageCodes.BLACK_PAWN);
		}
	}
	
	public void draw(PApplet marker) {}

	@Override
	public ArrayList<Location> calcMoveLocs() {
		ArrayList<Location> locs = new ArrayList<Location>();
		locs.add(loc);
		int row = loc.getRow(), col = loc.getCol();
		if(white) {
			if(board.inBounds(row, col+1) && board.get(row, col+1) == null) {
				locs.add(new Location(row, col+1));
			}
		}
		else {
			if(board.inBounds(row, col-1) && board.get(row, col-1) == null) {
				locs.add(new Location(row, col-1));
			}
		}
		return locs;
	}

	@Override
	public Location getMoveLoc(ArrayList<Location> moves) {
		return moves.get(moves.size()-1);
	}

	@Override
	public ArrayList<GamePiece> getAttackTargets() {
		ArrayList<GamePiece> toAttack = new ArrayList<GamePiece>();
		int row = loc.getRow(), col = loc.getCol();
		if(white) {
			if(board.inBounds(row+1, col+1) && board.get(row+1, col+1) != null) {
				toAttack.add(board.get(row+1, col+1));
			} if(board.inBounds(row-1, col+1) && board.get(row-1, col+1) == null) {
				toAttack.add(board.get(row-1, col+1));
			}
		}
		if(white) {
			if(board.inBounds(row+1, col-1) && board.get(row+1, col-1) != null) {
				toAttack.add(board.get(row+1, col-1));
			} if(board.inBounds(row-1, col-1) && board.get(row-1, col-1) == null) {
				toAttack.add(board.get(row-1, col-1));
			}
		}
		return toAttack;
	}

}