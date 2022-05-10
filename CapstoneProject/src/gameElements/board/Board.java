package gameElements.board;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import core.DrawingSurface;
import core.ImageCodes;
import gameElements.pieces.GamePiece;
import processing.core.PApplet;
import processing.core.PImage;

public class Board {
	private GamePiece[][] board;
	private ArrayList<PImage> images;
	
	public Board(int x, int y) {
		board = new GamePiece[x][y];
	}
	
	public Board() {
		board = new GamePiece[10][10];
		images = null;
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
					surface.image(img, x, y, sqHeight-2, sqHeight-2);		// assumes that height is larger than width
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
	
//	public void play() {
//		
//	}
	
}
