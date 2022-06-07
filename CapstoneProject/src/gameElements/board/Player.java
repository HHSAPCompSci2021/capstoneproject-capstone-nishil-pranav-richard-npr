package gameElements.board;

import java.util.ArrayList;
import gameElements.pieces.*;


/**
 * Represents a player in the game. The class stores information regarding cards, what side the player is on,
 * and the energy.
 * @author Pranav Gunhal
 * @version 01
 * 
 * */
public class Player {
	private ArrayList<Card> cards;
	private boolean isWhite;
	private int energy;
	
	/**
	 * Constructs a new Player of either the black or white side, and on a Board.
	 * @param b The Board on which this player is to be placed
	 * @param white Whether the player is on the white side or no (false represents the black side)
	 * */
	public Player(Board b, boolean white) {
		ArrayList<GamePiece> p = GamePiece.getSet(b, white);
		cards = new ArrayList<Card>();
		for(GamePiece piece : p) {
			cards.add(new Card(piece.getName(), piece.getHealth(), piece.getDamage(), piece.getEnergy(), piece.getImgCode()));
		}
		isWhite = white;
		energy = 10;
	}
	
	/**
	 * Gets the Cards that this player can use
	 * @return 5 cards that hold references to create a new Queen, Rook, Knight, Bishop, and Pawn
	 * */
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	/**
	 * Returns the side of this player
	 * @return Whether the player is on the white side or not
	 * */
	public boolean isWhite() {
		return isWhite;
	}
	
	/**
	 * Returns the energy of this player
	 * @return The energy of this player
	 * */
	public int getEnergy() {
		return energy;
	}
	
	/**
	 * Decrements the energy of this player 
	 * @param used The energy that is used by this player
	 * */
	public void useEnergy(int used) {
		energy-=used;
		if(energy < 0) {
			energy = 0;
		}
	}
	
	/**
	 * Adds energy to this player
	 * @param got The energy to be added to this player
	 * */
	public void addEnergy(int got) {
		energy+=got;
		if(energy > 10) {
			energy = 10;
		}
	}
	

}
