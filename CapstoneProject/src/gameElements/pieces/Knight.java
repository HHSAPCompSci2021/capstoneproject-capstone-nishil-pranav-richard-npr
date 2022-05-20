package gameElements.pieces;

import java.util.ArrayList;

import core.ImageCodes;
import gameElements.board.Board;
import gameElements.board.Location;
import io.grpc.netty.shaded.io.netty.util.internal.MathUtil;
import processing.core.PApplet;

public class Knight extends GamePiece {
	
	boolean freeLife;
	
	public Knight(int r, int c, Board brd, boolean wht) {
		super(r, c, brd, wht);
		freeLife = true;
		health = 20;
		fullHealth = health;
		damage = 8;
		maxDist = 2;
		energy = 2;
		range = 1;
		if (wht) {
			super.setImgCode(ImageCodes.WHITE_KNIGHT);
		} else {
			super.setImgCode(ImageCodes.BLACK_KNIGHT);
		}
	}
	
	public void draw(PApplet marker) {}

	@Override
	public ArrayList<Location> calcMoveLocs() {
		ArrayList<Location> locs = new ArrayList<Location>();
		int row = loc.getRow(), col = loc.getCol();
		for(int r = row-2; r <= row+2; r++) {
			for(int c = col-2; c <= col+2; c++) {
				if(board.inBounds(r,  c)) {
					if (board.isEmpty(r, c)){
						System.out.println("distance: " + loc.getDistanceFrom(new Location(r, c)));
						Location temp = new Location(r, c);
						if(loc.getDistanceFrom(temp) > Math.sqrt(5) - 0.001 && loc.getDistanceFrom(temp) < Math.sqrt(5) + 0.001) {
							locs.add(temp);
						}
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
				if(super.isWhite()) {
					if(l.getCol() > toPick.getCol() ) {
						toPick = l;
					}
					else if (l.getCol() == toPick.getCol() && Math.abs((double)l.getRow()-board.getHeight()/2) < Math.abs((double)toPick.getRow()-board.getHeight()/2)) {
						toPick = l;
					}
				} else {
					if(l.getCol() < toPick.getCol() ) {
						toPick = l;
					}
					else if (l.getCol() == toPick.getCol() && Math.abs((double)l.getRow()-board.getHeight()/2) < Math.abs((double)toPick.getRow()-board.getHeight()/2)) {
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
	
	@Override
	public void die() {
		if(freeLife) {
			freeLife = false;
			health = fullHealth;
		}
		else {
			super.die();
		}
	}
	
	public String getName() {
		return "Knight";
	}
}
