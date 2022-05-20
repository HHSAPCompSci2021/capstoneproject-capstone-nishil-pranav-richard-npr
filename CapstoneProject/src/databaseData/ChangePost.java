package databaseData;

import gameElements.pieces.GamePiece;

public class ChangePost extends Post {
	
	private int x;
	private int y;
	
//	private GamePiece piece;
	private boolean white;
	private String gamePieceName;
	
	public ChangePost() {
		super("PIECEADDED");
		this.x = 0;
		this.y = 0;
		this.white = true;
		this.gamePieceName = null;
	}
	
	public ChangePost(int x, int y, boolean white, String gamePieceName) {
		super("PIECEADDED");
		this.x = x;
		this.y = y;
		this.white = white;
		this.gamePieceName = gamePieceName;
	}
	
//	public ChangePost(int x, int y, GamePiece piece) {
//		super("PIECEADDED");
//		this.x = x;
//		this.y = y;
//		this.piece = piece;
//	}
	
	public String getGamePieceName() {
		return gamePieceName;
	}

	public void setGamePieceName(String gamePieceName) {
		this.gamePieceName = gamePieceName;
	}

	public boolean isWhite() {
		return white;
	}

	public void setWhite(boolean white) {
		this.white = white;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

//	public void setPiece(GamePiece piece) {
//		this.piece = piece;
//	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
//	public GamePiece getPiece() {
//		return piece;
//	}
	
	public String toString() {
		return String.format("ChangePost %d (%d, %d)", this.hashCode(), x, y);
//		return String.format("ChangePost %d (%d, %d) %s", this.hashCode(), x, y, piece.toString());
	}
	
	// TODO: MAKE SURE THAT THE STRING NAME OF PIECE IS AN ACTUAL PIECE
}