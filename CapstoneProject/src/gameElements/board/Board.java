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
				surface.fill(18, 82, 18);
				surface.stroke(0);//TODO change later?
				surface.rect(x, y, sqWidth, sqHeight);
				
				// draw piece
				GamePiece piece = board[j][i];
				if (piece != null) {
					PImage img = images.get(piece.getImgCode());
					surface.imageMode(PConstants.CENTER);
					surface.image(img, x, y, (int)sqWidth, (int)sqHeight);	// assumes that height is larger than width
					surface.textAlign(PConstants.CENTER);
					surface.fill(0);
					surface.textSize(10);
					surface.text(piece.getHealth() + "/" + piece.getFullHealth(), x, y-10);
				}
				
				x+=sqWidth;
			}
			x = tempX;
			y+=sqHeight;
		}
	}
	
	public Point2D.Float getCoordFromIndex(float x, float y, float width, float height, int r, int c) {
		Point2D.Float coords = new Point2D.Float();
		coords.x = x+(width/board.length)*r;
		coords.y = y+(height/board[0].length)*c;
		return coords;
	}
	
	
	public GamePiece get(int x, int y) {
		if(x >= 0 && x < board.length && y >= 0 && y < board[0].length) {
			return board[x][y];
		}
		return null;
	}
	
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
	
	public int getHeight() {
		return board.length;
	}
	
	public int getWidth() {
		return board[0].length;
	}
	
	public boolean inBounds(int r, int c) {
		if(r >= 0 && c >= 0 && r < board.length && c < board[0].length) {
			return true;
		}
		return false;
	}
	
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
		
		System.out.println(whiteKingHP + "    " + blackKingHP);
		
	}
	
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
	
	public void listToArray(ArrayList<ArrayList<GamePiece>> list) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = list.get(i).get(j);
			}
		}
	}
	
	// nulls array so it's safe to post. store data before using this
	public void removeArray() {
		this.board = null;
	}
	
	
	
	// TODO: javadoc
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
