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

	private DatabaseReference boardRef;
	private BoardPost boardPost;
	private Board board;
	
	public ScreenOnlineGame(DrawingSurface surface) {
		super(1200,600);
		this.surface = surface;
		this.board = null;
	}
	
	public void draw() {
		
		surface.background(255);
<<<<<<< Updated upstream
		
		
//		board = boardPost.getBoard();
//		board = surface.getBoard();
		board = new Board();
		board.draw(surface, 0, 0, DRAWING_WIDTH, DRAWING_HEIGHT);
		
	}
	
	public void keyPressed() {
		if (surface.key == 'c') {	
			if (board == null) return;
			board.add(new Queen(0, 0, board, true));
//			boardPost.getReference().setValueAsync(boardPost);
//			boardPost.updateGrid();
			
			System.out.println("        " + board.get(0,0).toString());
			boardPost.getReference().removeValueAsync();		// remove board from database
			BoardPost post = new BoardPost();
			post.setBoard(board);
			board.removeArray();
			DatabaseReference boardRef = surface.postData(post);
			
//			DatabaseReference boardPost = surface.postData(this.boardRef);			// error with class mapping???
//			surface.postData(boardPost);
			
//			String ref = boardPost.getReference().toString();
//			ref = ref.substring(0,ref.lastIndexOf("	/"));
//			System.out.println(ref);
			
			// https://chessroyale-e5d70-default-rtdb.firebaseio.com/-N2Nr20CijKMEfScRik1
			
//			System.out.println(board.get(0, 0).getName());
//			System.out.println(boardPost.getReference());
=======
		if (boardRef == null) {
			surface.background(0);
			System.out.println(boardRef);
		} else {
			surface.text(boardRef.toString(), 50, (int)(50+Math.random()*300));
>>>>>>> Stashed changes
		}
	}
	
	public void setBoardRef(BoardPost post) {
		this.boardPost = post;
	}
	
	
}