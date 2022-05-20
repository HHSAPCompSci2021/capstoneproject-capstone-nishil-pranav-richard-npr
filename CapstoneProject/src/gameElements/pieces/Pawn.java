package gameElements.pieces;

import java.util.ArrayList;

import core.ImageCodes;
import gameElements.board.Board;
import gameElements.board.Location;
import processing.core.PApplet;

/**
 * the Pawn represents a pawn on the chess board
 * @author hfeng636
 *
 */
public class Pawn extends GamePiece{
	
	/**
	 * creates a new alive Pawn in the given row, column, board, and color
	 * @param r the row in which to place the piece
	 * @param c the column in which to place the piece
	 * @param brd the board in which to place the piece
	 * @param wht the color of the piece
	 */
	public Pawn(int r, int c, Board brd, boolean wht) {
		super(r, c, brd, wht);
		health = 25;
		fullHealth = health;
		damage = 5;
		maxDist = 3;//does nothing
		energy = 1;
		range = 3;//does nothing
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
		if(isWhite()) {
			if(board.inBounds(row, col+1) && board.isEmpty(row, col+1)) {
				locs.add(0, new Location(row, col+1));
			}
		}
		else {
			if(board.inBounds(row, col-1) && board.isEmpty(row, col-1)) {
				locs.add(0, new Location(row, col-1));
			}
		}
		return locs;
	}

	@Override
	public Location getMoveLoc(ArrayList<Location> moves) {
		return moves.get(0);
	}

	@Override
	public ArrayList<GamePiece> getAttackTargets() {
		ArrayList<GamePiece> toAttack = new ArrayList<GamePiece>();
		int row = loc.getRow(), col = loc.getCol();
		if(isWhite()) {
			if(board.inBounds(row+1, col+1) && board.get(row+1, col+1) != null) {
				toAttack.add(board.get(row+1, col+1));
			} if(board.inBounds(row-1, col+1) && board.get(row-1, col+1) != null) {
				toAttack.add(board.get(row-1, col+1));
			}
		}
		else {
			if(board.inBounds(row+1, col-1) && board.get(row+1, col-1) != null) {
				toAttack.add(board.get(row+1, col-1));
			} if(board.inBounds(row-1, col-1) && board.get(row-1, col-1) != null) {
				toAttack.add(board.get(row-1, col-1));
			}
		}
		return toAttack;
	}

	
	public String getName() {
		return "Pawn";
	}
}
