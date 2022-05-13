package gameElements.board;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import core.DrawingSurface;
import core.ImageCodes;
import gameElements.pieces.GamePiece;
import processing.core.PConstants;
import processing.core.PImage;

public class Board {
	private GamePiece[][] board;
	private ArrayList<PImage> images;
	
	private int whiteKingHP;
	private int blackKingHP;
	
	public Board(int x, int y) {
		board = new GamePiece[x][y];
	}
	
	public Board() {
		board = new GamePiece[10][10];
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
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				// draw outer box
				surface.fill(255);
				surface.stroke(0);
				surface.rect(x, y, sqWidth, sqHeight);
				
				// draw piece
				GamePiece piece = board[j][i];
				if (piece != null) {
//					System.out.println(piece.getImgCode());
					PImage img = images.get(piece.getImgCode());
					//img.resize((int)sqHeight-2, (int)sqHeight-2);
					surface.image(img, x+sqWidth/2, y+sqHeight/2, (int)sqHeight-2, (int)sqHeight-2);	// assumes that height is larger than width
					surface.textMode(PConstants.CENTER);
					surface.fill(0);
					surface.text(piece.getHealth() + "/" + piece.getFullHealth(), x+sqWidth/2, y);
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
		int x = loc.getCol();
		int y = loc.getRow();
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
		x = p.x - x;
		y = p.y - y;
		
		if (x < 0 || y < 0 || x > width || y > height) return null;
		
		x = x/width*10;
		y = y/height*10;
		
		return new Point((int)x, (int)y);
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
			GamePiece piece = board[0][i];
			if (piece != null) {
				boolean white = piece.isWhite();
				if (!white) {
					damageKing(piece.getDamage(), true);
				}
			}
		}
		for (int i = 0; i < board.length; i++) {			// right
			GamePiece piece = board[9][i];
			if (piece != null) {
				boolean black = !piece.isWhite();
				if (!black) {
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
	
	/**
	 * Checks if the game is over by king health being <= 0.
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
	
}
