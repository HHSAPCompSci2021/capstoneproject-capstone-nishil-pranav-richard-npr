package gameElements.pieces;

import java.util.ArrayList;

import core.ImageCodes;
import gameElements.board.Board;
import gameElements.board.Location;
import processing.core.PApplet;

public class Queen extends GamePiece{
	
	private double queenDamageBonus;
	
	public Queen(int r, int c, Board brd, boolean wht) {
		super(r, c, brd, wht);
		queenDamageBonus = 1;
		health = 50;
		fullHealth = health;
		damage = 4;
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
		for(int r = row-1; r <= row+1; r++) {
			for(int c = col-1; c <= col+1; c++) {
				if(board.inBounds(r,  c)) {
					if (board.isEmpty(r, c)){
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
			queenDamageBonus = 1;
			for(Location l : moves) {
				if(super.isWhite()) {
					if(l.getCol() > toPick.getCol() && toPick.getRow() == l.getRow()) {
						toPick = l;
					}
				} else {
					if(l.getCol() < toPick.getCol() && toPick.getRow() == l.getRow()) {
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
				if(toPick.getDistanceFrom(target.getLocation()) <= Math.sqrt(range*range+range*range)+0.001 ) { //already in range
					if(l.getDistanceFrom(target.getLocation()) <= Math.sqrt(range*range+range*range)+0.001) {
						if(l.getDistanceFrom(target.getLocation()) > toPick.getDistanceFrom(target.getLocation())) {
							toPick = l;
						}
					}
				}
				else {// not in range, get in range
					if(l.getDistanceFrom(target.getLocation()) < toPick.getDistanceFrom(target.getLocation())) {
						toPick = l;
					}
				}
			}
		}
		return toPick;
	}

	@Override
	public int getDamage() {
		System.out.println("Queen damage: " + queenDamageBonus);
		return damage * (int)queenDamageBonus;
	}
	
	@Override
	public ArrayList<GamePiece> getAttackTargets() {
		if(target != null) {	
			ArrayList<GamePiece> toAttack = new ArrayList<GamePiece>();
			if(loc.getDistanceFrom(target.getLocation()) <= Math.sqrt(range*range+range*range)+ 0.001) {
				toAttack.add(target);
				queenDamageBonus++;
				return toAttack;
			}
			return toAttack;
		}
		else {
			return null;
		}
	}
	
	public String getName() {
		return "Queen";
	}

}
