package gameElements.pieces;

import java.util.ArrayList;

import core.ImageCodes;
import gameElements.board.Board;
import gameElements.board.Location;
import processing.core.PApplet;

public class Bishop extends GamePiece{
	private double bishopDamageBonus;
	
	public Bishop(int r, int c, Board brd, boolean wht) {
		super(r, c, brd, wht);
		bishopDamageBonus = 0;
		health = 50;
		fullHealth = health;
		damage = 10;
		maxDist = 3;
		energy = 3;
		range = 1;
		if (wht) {
			super.setImgCode(ImageCodes.WHITE_BISHOP);
		} else {
			super.setImgCode(ImageCodes.BLACK_BISHOP);
		}
	}
	
	public void draw(PApplet marker) {}

	@Override
	public ArrayList<Location> calcMoveLocs() {
		ArrayList<Location> locs = new ArrayList<Location>();
		int row = loc.getRow(), col = loc.getCol();
		for(int i = -3; i <= 3; i++) {
			if(board.inBounds(row + i, col + i) && board.isEmpty(row + i, col + i)) {
				locs.add(new Location(row+i, col+i));
			}
			if(board.inBounds(row-i, col+i) && board.isEmpty(row-i, col+i)) {
				locs.add(new Location(row-i, col+i));
			}
		}	
		return locs;
	}

	@Override
	public Location getMoveLoc(ArrayList<Location> moves) {
		Location toPick = loc;
		if(target == null) {
			bishopDamageBonus = 0;
			for(Location l : moves) {
				if(super.isWhite()) {
					if(l.getCol() > toPick.getCol() ) {
						if(l.getDistanceFrom(loc) < 1.5) {
							toPick = l;
						}
					}
					else if (l.getCol() == toPick.getCol() && Math.abs((double)l.getRow()-board.getHeight()/2) < Math.abs((double)toPick.getRow()-board.getHeight()/2)) {
						if(l.getDistanceFrom(loc) < 1.5) {
							toPick = l;
						}
					}
				} else {
					if(l.getCol() < toPick.getCol() ) {
						if(l.getDistanceFrom(loc) < 1.5) {
							toPick = l;
						}
					}
					else if (l.getCol() == toPick.getCol() && Math.abs((double)l.getRow()-board.getHeight()/2) < Math.abs((double)toPick.getRow()-board.getHeight()/2)) {
						if(l.getDistanceFrom(loc) < 1.5) {
							toPick = l;
						}
					}
				}
			}
		}
		else {
			if(loc.getDistanceFrom(target.getLocation()) <= Math.sqrt(range*range+range*range)+0.001) {
				bishopDamageBonus = 0;
				return loc;	
			}
			for(Location l : moves) {
				if(l.getDistanceFrom(target.getLocation()) < toPick.getDistanceFrom(target.getLocation())) {
					toPick = l;
				}
			}
		}
		bishopDamageBonus += 0.5*toPick.getDistanceFrom(loc);
		return toPick;
	}

	@Override
	public int getDamage() {
		return (int)((double)damage * (1.0+bishopDamageBonus));
	}
	
	@Override
	public ArrayList<GamePiece> getAttackTargets() {
		if(target != null) {	
			ArrayList<GamePiece> toAttack = new ArrayList<GamePiece>();
			if(loc.getDistanceFrom(target.getLocation()) <= Math.sqrt(range*range+range*range)+ 0.001) {
				toAttack.add(target);
				return toAttack;
			}
			return toAttack;
		}
		else {
			return null;
		}
	}
	
	public String getName() {
		return "Bishop";
	}

}
