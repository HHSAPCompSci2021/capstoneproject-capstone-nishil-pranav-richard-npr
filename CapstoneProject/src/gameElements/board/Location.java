package gameElements.board;
import gameElements.pieces.*;
public class Location {
	private int row, col;
	public Location() {
		row = 0;
		col = 0;
	}
	public Location(int r, int c) {
		row = r;
		col = c;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public void setRow(int newRow) {
		row = newRow;
	}
	public void setCol(int newCol) {
		col = newCol;
	}

/*
	private GamePiece gp;
	
	public Location(GamePiece g) {
		gp = g;
	}
	
	public GamePiece get() {
		return gp;
	}
	
	*/

}
