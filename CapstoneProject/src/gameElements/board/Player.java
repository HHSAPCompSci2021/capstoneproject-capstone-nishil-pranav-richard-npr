package gameElements.board;

import java.util.ArrayList;
import gameElements.pieces.*;


/**
 * Represents a player in the game. The class stores information 
 * 
 * 
 * */
public class Player {
	private ArrayList<Card> cards;
	private boolean isWhite;
	private int energy;
	
	public Player(Board b, boolean white) {
		ArrayList<GamePiece> p = GamePiece.getSet(b, white);
		cards = new ArrayList<Card>();
		for(GamePiece piece : p) {
			cards.add(new Card(piece.getName(), piece.getHealth(), piece.getDamage(), piece.getEnergy(), piece.getImgCode()));
		}
		isWhite = white;
		energy = 10;
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public boolean isWhite() {
		return isWhite;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public void useEnergy(int used) {
		energy-=used;
		if(energy < 0) {
			energy = 0;
		}
	}
	
	public void addEnergy(int got) {
		energy+=got;
		if(energy > 10) {
			energy = 10;
		}
	}
	

}
