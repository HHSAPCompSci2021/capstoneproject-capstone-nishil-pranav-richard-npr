package databaseData;


/**
 * The ChangePost class a piece to be added to the board.
 * @author Nishil Anand
 * */
public class ChangePost extends Post {
	
	private int x;
	private int y;
	
	private boolean white;
	private String gamePieceName;
	
	
	/**
	 * Constructs a new ChangePost
	 */
	public ChangePost() {
		super("PIECEADDED");
		this.x = 0;
		this.y = 0;
		this.white = true;
		this.gamePieceName = null;
	}
	
	/**
	 * Constructs a new ChangePost
	 * 
	 * @param x x-coordinate of the new piece
	 * @param y y-coordinate of the new piece
	 * @param white if the piece is white
	 * @param gamePieceName name of the piece with the first letter capitalized
	 * 
	 * @pre the x and y coordinates are in the grid
	 * @pre the gamePieceName is the name of an actual piece (Bishop, Knight, Pawn, Queen, or Rook)
	 * 
	 */
	public ChangePost(int x, int y, boolean white, String gamePieceName) {
		super("PIECEADDED");
		this.x = x;
		this.y = y;
		this.white = white;
		if (validName(gamePieceName))
			this.gamePieceName = gamePieceName;	
	}
	
	
	/**
	 * Returns the name of the piece.
	 * @return the name of the piece
	 */
	public String getGamePieceName() {
		return gamePieceName;
	}

	/**
	 * Sets the name of the piece to gamePieceName.
	 * @param gamePieceName the name of the piece
	 */
	public void setGamePieceName(String gamePieceName) {
		this.gamePieceName = gamePieceName;
	}

	/**
	 * Returns true if the piece is white, false if it is black.
	 * @return if this piece is white
	 */
	public boolean isWhite() {
		return white;
	}

	/**
	 * Sets if the piece is white to white.
	 * @param white if the piece should be white
	 */
	public void setWhite(boolean white) {
		this.white = white;
	}

	/**
	 * Sets the x-coordinate of the piece to x.
	 * @param x the x-coordinate of the piece
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Sets the y-coordinate of the piece to y.
	 * @param y the y-coordinate of the piece
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * The x-coordinate of the piece.
	 * @return the x-coordinate of the piece
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * The y-coordinate of the piece.
	 * @return the y-coordinate of the piece
	 */	
	public int getY() {
		return y;
	}
	
	/**
	 * Returns a String representing this class containing a hashCode, the x-coordinate, and the y-coordinate
	 * @return a String representing this class
	 */
	public String toString() {
		return String.format("ChangePost %d (%d, %d)", this.hashCode(), x, y);
	}
	
	
	private boolean validName(String gamePieceName) {
		if (gamePieceName.matches("Bishop")) {
			return true;
		} else if (gamePieceName.matches("Knight")) {
			return true;
		} else if (gamePieceName.matches("Pawn")) {
			return true;
		} else if (gamePieceName.matches("Queen")) {
			return true;
		} else if (gamePieceName.matches("Rook")) {
			return true;
		} else {
			System.err.println("Unknown piece name " + gamePieceName);
			for (StackTraceElement v: Thread.currentThread().getStackTrace()) {
			    System.err.println("    " + v);
			}
			return false;
		}
	}
	
}