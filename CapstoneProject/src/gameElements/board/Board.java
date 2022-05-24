package gameElements.board;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import core.DrawingSurface;
import gameElements.pieces.GamePiece;
import processing.core.PConstants;
import processing.core.PImage;

/**
 * 
 * @author Pranav Gunhal
 * @author Richard Feng
 * @author Nishil Anand
 * */
public class Board {
	private GamePiece[][] board;
	private ArrayList<PImage> images;
	
	private int whiteKingHP;
	private int blackKingHP;
	
	public Board(int x, int y) {
		board = new GamePiece[x][y];
		images = null;
		whiteKingHP = 100;
		blackKingHP = 100;
	}
	
	public Board() {
		board = new GamePiece[15][15];
		images = null;
		whiteKingHP = 100;
		blackKingHP = 100;
	}
	
	/**
	 * Draws this board on a DrawingSurface PApplet
	 * @param surface The surface (PApplet) on which this board is to be drawn on
	 * @param x The leftmost point of the board
	 * @param y The topmost point of the board
	 * @param width The width of the board
	 * @param height The height of the board
	 * */
	public void draw(DrawingSurface surface, float x, float y, float width, float height) {
		if (images == null) {
			images = surface.getImages();
		}
		float sqWidth = width/board.length;
		float sqHeight = height/board[0].length;
		float tempX = x;
		for(int j = 0; j < board.length; j++) {
			for(int i = 0; i < board[j].length; i++) {
				// draw outer box
				surface.fill(220, 180, 42);
				surface.stroke(0);
				surface.rect(x, y, sqWidth, sqHeight);
				
				// draw piece
				GamePiece piece = board[j][i];
				if (piece != null) {
					PImage img = images.get(piece.getImgCode());
					surface.imageMode(PConstants.CENTER);
					surface.image(img, x, (int)(y+3), (int)sqWidth, (int)(sqHeight*0.8));	// assumes that height is larger than width
					surface.textAlign(PConstants.CENTER);
					surface.fill(0);
					surface.textSize(10);
					surface.fill(255, 0, 0);
					surface.rect(x, y- sqHeight/3, (int)(sqWidth*0.8), (int)(sqHeight/6));
					surface.fill(0, 255, 0);
					double ratio = (double)piece.getHealth()/(double)piece.getFullHealth();
					if(ratio < 0) { ratio = 0;}
					surface.rect(x-(int)(sqWidth*0.8-sqWidth*0.8*ratio)/2, y- sqHeight/3, (int)(sqWidth*0.8*ratio), (int)(sqHeight/6));
					surface.fill(0);
				}
				
				x+=sqWidth;
			}
			x = tempX;
			y+=sqHeight;
		}
	}
	
	/**
	 * Returns the xy coordinates of a point given the index
	 * @param x The leftmost x value on the board
	 * @param y The topmost y value on the board
	 * @param width The width of the board
	 * @param height The height of the board
	 * @param r The row of the index
	 * @param c The column of the index
	 * @return The coordinate at the given index on this board
	 * */
	public Point2D.Float getCoordFromIndex(float x, float y, float width, float height, int r, int c) {
		Point2D.Float coords = new Point2D.Float();
		coords.x = x+(width/board.length)*r;
		coords.y = y+(height/board[0].length)*c;
		return coords;
	}
	
	/**
	 * Returns the GamePiece at a certain location
	 * @param x The row of the GamePiece
	 * @param y The column of the GamePiece
	 * @return the GamePiece at board[x][y], if it is valid
	 * */
	public GamePiece get(int x, int y) {
		if(x >= 0 && x < board.length && y >= 0 && y < board[0].length) {
			return board[x][y];
		}
		return null;
	}
	
	/**
	 * Adds a GamePiece to an index on this board
	 * @param l The GamePiece to be added
	 * @param x The row where l is to be added
	 * @param the column where l is to be added
	 * @post If board[x][y] is valid, it will be set to l
	 * */
	public void set(GamePiece l, int x, int y) {
		if(x >= 0 && x < board.length && y >= 0 && y < board[0].length) {
			board[x][y] = l;
		}
	}
	
	/**
	 * Adds a GamePiece piece to this Board using piece's Location
	 * @param piece GamePiece to add to the board
	 * @pre piece must not be null. piece must also have properly a initialized Location loc which is in the board 
	 */
	public void add(GamePiece piece) {
		if (piece == null) throw new NullPointerException("piece is null");
		Location loc = piece.getLocation();
		if (loc == null) throw new NullPointerException("piece.getLocation() is null");
		int x = loc.getRow();
		int y = loc.getCol();
		if (!(x >= 0 && x < board.length && y >= 0 && y < board[0].length)) throw new ArrayIndexOutOfBoundsException("piece.getLocation() does not fit in the board");
		board[x][y] = piece;
	}
	
	/**
	 * Returns the height of this board
	 * @return the height of this board
	 * */
	public int getHeight() {
		return board.length;
	}
	
	/**
	 * Returns the width of this board
	 * @return the width of this board
	 * */
	public int getWidth() {
		return board[0].length;
	}
	
	/**
	 * Determines whether a certain index is within the bounds of this board
	 * @param r The row which is being tested
	 * @param c The column which is being tested
	 * @return True if board[r][c] is a valid index, False otherwise
	 * */
	public boolean inBounds(int r, int c) {
		if(r >= 0 && c >= 0 && r < board.length && c < board[0].length) {
			return true;
		}
		return false;
	}
	
	/**
	 * Determines whether the Board is empty at a certain Location
	 * @param r The row which is being tested
	 * @param c The column which is being tested
	 * @return True if board[i][c] is null, False otherwise. 
	 * */
	public boolean isEmpty(int r, int c) {
		if(board[r][c] == null) {
			return true;
		}
		return false;
	}
	/**
	 * Determines the index of the grid which matches with a particular pixel coordinate.
	 * This supports interaction with the grid using mouse clicks in the window.
	 * 
	 * Example usage (given board is a Board and surface is a DrawingSurface:
	 * Point loc = board.clickToIndex(surface.actualCoordinatesToAssumed(new Point(surface.mouseX, surface.mouseY)), boardX, boardY, boardWidth, boardHeight);
	 * 
	 * @param p A Point object containing a graphical pixel coordinate.
	 * @param x The x pixel coordinate of the upper left corner of the grid drawing. 
	 * @param y The y pixel coordinate of the upper left corner of the grid drawing.
	 * @param width The pixel width of the grid drawing.
	 * @param height The pixel height of the grid drawing.
	 * @return A Point object representing a coordinate within the grid (x, y) (column, row).
	 */
	public Point clickToIndex(Point p, float x, float y, float width, float height) {
		float tileWidth = width/(float)board.length;
		float tileHeight = height/(float)board[0].length;

		float x1 = p.x - x + tileWidth/2;
		float y1 = p.y - y + tileHeight/2;
		
		System.out.println("x and y: " + x1 + " " + y1);
		
		if (x1 < 0 || y1 < 0 || x1 > width || y1 > height) return null;
		
		x1 = x1/tileWidth;
		y1 = y1/tileHeight;
		
		
		return new Point((int)x1, (int)y1);
	}
	
	/**
	 * Plays the game according to GamePiece behavior
	 * @post the Locations and health of some or all GamePieces may be affected
	 * */
	public void play() {
		
		// Note: to prevent calling act on the same GamePiece multiple times (if the GamePiece moves it might get called again), all GamePieces are gathered into an ArrayList then act is called on each GamePiece
		// call act on all pieces
		ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
		for (GamePiece[] i : board) {
			for (GamePiece piece : i) {
				if (piece != null) {
					pieces.add(piece);
				}
			}
		}
		for (GamePiece piece: pieces) {
			piece.act();
		}
		
		// damage king
		for (int i = 0; i < board.length; i++) {			// left
			GamePiece piece = board[i][0];
			if (piece != null) {
				boolean white = piece.isWhite();
				if (!white) {
					kingDamage(i, 0);
					damageKing(piece.getDamage(), true);
				}
			}
		}
		for (int i = 0; i < board.length; i++) {			// right
			GamePiece piece = board[i][board[0].length-1];
			if (piece != null) {
				boolean black = !piece.isWhite();
				if (!black) {
					kingDamage(i, board[0].length-1);
					damageKing(piece.getDamage(), false);
				}
			}
		}
		
//		System.out.println(whiteKingHP + "    " + blackKingHP);
		
	}
	
	/**
	 * Returns the health of the king of a certain side.
	 * @param white Whether the king is on the white side or not
	 * @return The health of the corresponding king
	 * */
	public int getKingHealth(boolean white) {
		if (white) {
			return whiteKingHP;
		} else {
			return blackKingHP;
		}
	}
	
	/**
	 * Damages the king. If the king dies, prints that the game has ended and updates gameInProgress.
	 * 
	 * @param dmg how much damage to inflict
	 * @param white true to damage white king, false to damage black king
	 */
	public void damageKing(int dmg, boolean white) {
		if (white) {
			whiteKingHP -= dmg;
		} else {
			blackKingHP -= dmg;
		}
	}
	
	private void kingDamage(int r, int c) {
		board[r][c].takeDamage(board[r][c].getDamage());
	}
 	
	/**
	 * Removes a GamePiece from the specified location
	 * @param r The row of the GamePiece to be removed
	 * @param c The column of the GamePiece to be removed
	 * @post board[r][c] is null
	 * */
	public void remove(int r, int c) {
		board[r][c] = null;
	}
	
	/**
	 * Checks if the game is over by king health being is less than or equal to 0.
	 * 
	 * @return 0 if the game is not over, 1 if white won, 2 if black won, 3 if draw (both kings die at same time)
	 */
	public int checkGameOver() {
		int result = 0;
		if (whiteKingHP <= 0) {
			whiteKingHP = 0;
			result += 1;
		}
		if (blackKingHP <= 0) {
			blackKingHP = 0;
			result += 2;
		}
		return result;
	}
	
	
	// NETWORKING
	
	/**
	 * Converts a 2D ArrayList of GamePieces into GamePieces on this Board
	 * @param list The 2D ArrayList of GamePieces
	 * @post this Board is a 2D array representation of list
	 * */
	public void listToArray(ArrayList<ArrayList<GamePiece>> list) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = list.get(i).get(j);
			}
		}
	}
	
	/**
	 * Makes this board null so its safe to post
	 * @post this Board is completely null
	 * */
	public void removeArray() {
		this.board = null;
	}
	
	
	
	/**
	* Converts this Board into a 2D ArrayList of GamePieces
	* @return a 2D ArrayList containing GamePieces as this Board does
	*/
	public ArrayList<ArrayList<GamePiece>> getBoard() {
		ArrayList<ArrayList<GamePiece>> grid = new ArrayList<ArrayList<GamePiece>>();
		GamePiece[][] gridArray = board;
		for (int i = 0; i < gridArray.length; i++ ) {
			grid.add(new ArrayList<GamePiece>());
			for (int j = 0; j < gridArray[i].length; j++ ) {
				ArrayList<GamePiece> sub = grid.get(i);
				GamePiece v = gridArray[i][j];
				sub.add(v);
			}
		}
		return grid;
	}
	
}
