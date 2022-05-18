package databaseData;

import gameElements.board.Board;

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