package gameElements.board;

import java.util.ArrayList;
import gameElements.pieces.*;
public class Player {
	private ArrayList<Card> cards;
	private boolean isWhite;
	
	public Player(Board b, boolean white) {
		ArrayList<GamePiece> p = GamePiece.getSet(b, white);
		cards = new ArrayList<Card>();
		for(GamePiece piece : p) {
			cards.add(new Card(piece.getName(), piece.getHealth(), piece.getDamage(), piece.getImgCode()));
		}
		isWhite = white;
		
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public boolean isWhite() {
		return isWhite;
	}
	

}
