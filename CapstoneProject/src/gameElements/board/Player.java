package gameElements.board;

import java.util.ArrayList;
import gameElements.pieces.*;
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
		energy = 2;
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
	
	public void getEnergy(int got) {
		energy+=got;
		if(energy > 10) {
			energy = 0;
		}
	}
	

}
