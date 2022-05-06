package gameElements.pieces;

import java.util.ArrayList;

import core.ImageCodes;
import gameElements.board.Board;
import gameElements.board.Location;
import processing.core.PApplet;

public class Queen extends GamePiece{
	
	public Queen(int r, int c, Board brd, boolean wht) {
		super(r, c, brd, wht);
		health = 10;
		damage = 10;
		maxDist = 3;
		range = 3;
		if (wht) {
			super.setImgCode(ImageCodes.WHITE_QUEEN);
		} else {
			super.setImgCode(ImageCodes.BLACK_QUEEN);
		}
	}
	
	
	public void draw(PApplet marker) {}


	@Override
	public ArrayList<Location> calcMoveLocs() {
		ArrayList<Location> locs = new ArrayList<Location>();
		int row = loc.getRow(), col = loc.getCol();
		for(int i = 0; i < 8; i++) {
			for(int r = row + moveR[i]; r != row+moveR[i]*maxDist; r += moveR[i]) {
				for(int c = col + moveC[i]; c != col+moveC[i]*maxDist; c += moveC[i]) {
					if(board.inBounds(r, c) && board.get(r, c) == null) {
						locs.add(new Location(r, c));
					}
				}
			}
		}
		return locs;
	}
	
	@Override
	public Location getMoveLoc(ArrayList<Location> moves) {
		Location toPick = loc;
		if(target == null) {
			for(Location l : moves) {
				if(white) {
					if(l.getCol() > toPick.getCol()) {
						toPick = l;
					}
				} else {
					if(l.getCol() < toPick.getCol()) {
						toPick = l;
					}
				}
			}
		}
		else {
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
		if(loc.getDistanceFrom(target.getLocation()) < Math.sqrt(range*range+range*range)) {
			toAttack.add(target);
		}
		return toAttack;
	}

}
