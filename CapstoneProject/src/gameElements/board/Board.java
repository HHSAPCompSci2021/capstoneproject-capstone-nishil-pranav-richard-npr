package gameElements.board;

import java.awt.geom.Point2D;

import processing.core.PApplet;

public class Board {
	private Location[][] board;
	
	public Board(int x, int y) {
		board = new Location[x][y];
	}
	
	public Board() {
		board = new Location[10][10];
	}
	
	public void draw(PApplet drawer, float x, float y, float width, float height) {
		float sqWidth = width/board.length;
		float sqHeight = height/board[0].length;
		float tempX = x;
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				drawer.fill(255);
				drawer.stroke(0);
				drawer.rect(x, y, sqWidth, sqHeight);
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
	
	
	public Location get(int x, int y) {
		if(x >= 0 && x < board.length && y >= 0 && y < board[0].length) {
			return board[x][y];
		}
		return null;
	}
	
	public void set(Location l, int x, int y) {
		if(x >= 0 && x < board.length && y >= 0 && y < board[0].length) {
			board[x][y] = l;
		}
	}
	
}
