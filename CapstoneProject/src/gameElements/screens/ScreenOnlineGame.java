package gameElements.screens;

import java.awt.event.ActionListener;

import com.google.firebase.database.DatabaseReference;

import core.DrawingSurface;
import databaseData.BoardPost;
import gameElements.board.Board;
import gameElements.pieces.GamePiece;
import gameElements.pieces.Queen;

public class ScreenOnlineGame extends Screen {
	
	private DrawingSurface surface;
	private BoardPost boardRef;
	private Board board;
	
	public ScreenOnlineGame(DrawingSurface surface) {
		super(1200,600);
		this.surface = surface;
		this.board = null;
	}
	
	public void draw() {
		
		surface.background(255);
		
		if (boardRef == null) return;
		board = boardRef.getBoard();
		board.draw(surface, 0, 0, DRAWING_WIDTH, DRAWING_HEIGHT);
		
	}
	
	public void keyPressed() {
		if (surface.key == 'c') {	
			if (board == null) return;
			board.add(new Queen(0, 0, board, true));
			System.out.println(board.get(0, 0).getName());
		} else {
		}
	}
	
	public void setBoardRef(BoardPost post) {
		this.boardRef = post;
	}
	
	
}