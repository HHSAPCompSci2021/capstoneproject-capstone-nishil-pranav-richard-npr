package databaseData;

import gameElements.board.Board;


// TODO: this class probably doesn't need to exist (or can be replaced by something). It was originally made to store a Board but does not because it is better to just post changes to the Board. Firebase also can't store arrays so storing a Board would be tricky.  

/**
 * The BoardPost class represents a Post of a Board
 * @author Nishil Anand
 * */
public class BoardPost extends Post {
	
	private Board board;
	
	
	/**
	 * Creates a new BoardPost object
	 * */
	public BoardPost() {
		super("BOARD");
		this.board = new Board();
	}
	
	/**
	 * Constructs a new BoardPost object holding a board
	 * @param board the board that this class holds
	 * */
	public BoardPost(Board board) {
		super("BOARD");
		this.board = board;
	}
	
	
	/**
	 * Returns the board this BoardPost represents
	 * @return the board this BoardPost represents
	 * */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Sets a new board
	 * @param board the new board
	 */
	public void setBoard(Board board) {
		this.board = board;
	}
	
}