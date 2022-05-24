package gameElements.board;

/**
 * The Location class represents a spot on the Board.
 * @author Richard
 * */
public class Location {
	private int row, col;
	
	/**
	 * Creates a new Location at (0,0)
	 * */
	public Location() {
		row = 0;
		col = 0;
	}
	
	/**
	 * Constructs a new Location at the specified spot
	 * @param r The row of the Location
	 * @param c The column of the Location
	 * */
	public Location(int r, int c) {
		row = r;
		col = c;
	}
	
	/**
	 * Returns the row of this Location
	 * @return the row of this Location
	 * */
	public int getRow() {
		return row;
	}
	
	/**
	 * Returns the column of this Location
	 * @return the column of this Location
	 * */
	public int getCol() {
		return col;
	}
	
	/**
	 * Sets the row of this Location
	 * @param newRow the row of this Location
	 * */
	public void setRow(int newRow) {
		row = newRow;
	}
	
	/**
	 * Sets the column of this Location
	 * @param newCol the row of this Location
	 * */
	public void setCol(int newCol) {
		col = newCol;
	}
	
	/**
	 * Finds the distance between this Location and another Location
	 * @param other The Location to find the distance between
	 * @return The distance between this Location and other
	 * */
	public double getDistanceFrom(Location other) {
		double dRow = (double) row, dCol = (double) col, dRowOther = (double) other.getRow(), dColOther = (double)other.getCol();
		return Math.sqrt(Math.pow((dRow-dRowOther), 2) + Math.pow((dCol - dColOther), 2));
	}
}
