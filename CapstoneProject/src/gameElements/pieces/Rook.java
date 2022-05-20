package gameElements.pieces;

import java.util.ArrayList;

import core.ImageCodes;
import gameElements.board.Board;
import gameElements.board.Location;
import processing.core.PApplet;

/**
 * the Rook represents a rook on the chess board
 * @author hfeng636
 *
 */
public class Rook extends GamePiece{
	
	/**
	 * creates a new alive Rook in the given row, column, board, and color
	 * @param r the row in which to place the piece
	 * @param c the column in which to place the piece
	 * @param brd the board in which to place the piece
	 * @param wht the color of the piece
	 */
	public Rook(int r, int c, Board brd, boolean wht) {
		super(r, c, brd, wht);
		health = 120;
		fullHealth = health;
		damage = 8;
		maxDist = 3;
		energy = 4;
		range = 1;
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
		int row = loc.getRow(), col = loc.getCol();
		for(int i = -1; i <= 1; i++) {
			if(board.inBounds(row + i, col) && board.isEmpty(row + i, col)) {
				locs.add(new Location(row+i, col));
			}
			if(board.inBounds(row, col+i) && board.isEmpty(row, col+i)) {
				locs.add(new Location(row, col+i));
			}
		}	
		return locs;
	}
	
	@Override
	public Location getMoveLoc(ArrayList<Location> moves) {
		Location toPick = loc;
		if(target == null) {
			for(Location l : moves) {
				if(super.isWhite()) {
					if(l.getCol() > toPick.getCol() ) {
						toPick = l;
					}
				} else {
					if(l.getCol() < toPick.getCol() ) {
						toPick = l;
					}
				}
			}
		}
		else {
			if(loc.getDistanceFrom(target.getLocation()) <= Math.sqrt(range*range+range*range)+0.001) {
				return loc;
			}
			for(Location l : moves) {
				if(l.getDistanceFrom(target.getLocation()) < toPick.getDistanceFrom(target.getLocation())) {
					toPick = l;
				}
			}
		}
		return toPick;
	}

	@Override
	public ArrayList<GamePiece> getAttackTargets() {
		ArrayList<GamePiece> toAttack = new ArrayList<GamePiece>();
		int row = loc.getRow(), col = loc.getCol();
		for(int r = row-1; r <= row+1; r++) {
			for(int c = col-1; c <= col+1; c++) {
				if(board.inBounds(r, c) && !board.isEmpty(r, c) && board.get(r, c).isWhite() != white) {
					toAttack.add(board.get(r, c));
				}
			}
		}
		return toAttack;
	}
	
	@Override
	public String getName() {
		return "Rook";
	}
 	
}
