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
		energy = 5;
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
		for(int r = row-2; r <= row+2; r++) {
			for(int c = col-2; col <= row+2; col++) {
				if(board.inBounds(r,  c)) {
					if(loc.getDistanceFrom(new Location(r, c)) > 2 && loc.getDistanceFrom(new Location(r, c)) < 2.8) {
						//invalid pos 
					}
					else if (board.isEmpty(r, c)){
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
		if(loc.getDistanceFrom(target.getLocation()) <= Math.sqrt(range*range+range*range)+0.001) {
			toAttack.add(target);
			return toAttack;
		}
		return toAttack;
	}
	
	public String getName() {
		return "Queen";
	}

}
