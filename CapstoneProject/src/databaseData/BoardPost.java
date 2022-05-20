package databaseData;

import java.util.ArrayList;

import com.google.firebase.database.DatabaseReference;

import gameElements.board.Board;
import gameElements.pieces.GamePiece;

/**
 * The BoardPost class represents a Post of a Board
 * @author Nishil Anand
 * */
public class BoardPost extends Post {
	
	private Board board;
	private DatabaseReference ref;
	private ArrayList<ArrayList<GamePiece>> grid;
	
	private String whiteName;
	private String blackName;
	
	/**
	 * Creates a new BoardPost object
	 * */
	public BoardPost() {
		super("BOARD");
		this.board = new Board();
		updateGrid();
	}
	
	/**
	 * Constructs a new BoardPost object holding a board
	 * @param board the board that this class holds
	 * */
	public BoardPost(Board board) {
		super("BOARD");
		this.board = board;
		updateGrid();
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
		updateGrid();
	}
	
	public void setReference(DatabaseReference ref) {
		this.ref = ref;
	}
	
	public DatabaseReference getReference() {
		return ref;
	}
	
//	public ArrayList<ArrayList<GamePiece>> getGrid() {
//		return grid;
//	}
	
	public void updateGrid() {
//		if (this.board == null) return;
//		GamePiece[][] gridArray = board.getBoard();	
//		grid = new ArrayList<ArrayList<GamePiece>>();
//		for (int i = 0; i < gridArray.length; i++ ) {
//			for (int j = 0; j < gridArray[i].length; j++ ) {
//				grid.get(i).set(j, gridArray[i][j]);
//			}
//		}
		this.grid = board.getBoard();
	}
	
	public void setNames(String whiteName, String blackName) {
		this.whiteName = whiteName;
		this.blackName = blackName;
	}
	
	public String getWhiteName() {
		return this.whiteName;
	}
	
	public String getBlackName() {
		return this.blackName;
	}
	
}