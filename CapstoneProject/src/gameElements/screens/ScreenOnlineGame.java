package gameElements.screens;

import java.awt.event.ActionListener;

import com.google.firebase.database.DatabaseReference;

import core.DrawingSurface;
import databaseData.BoardPost;
import databaseData.ChangePost;
import gameElements.board.Board;
import gameElements.pieces.Bishop;
import gameElements.pieces.GamePiece;
import gameElements.pieces.Knight;
import gameElements.pieces.Pawn;
import gameElements.pieces.Queen;
import gameElements.pieces.Rook;

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
		
		
//		board = boardPost.getBoard();
//		board = surface.getBoard();
		board = new Board();
		board.draw(surface, 0, 0, DRAWING_WIDTH, DRAWING_HEIGHT);
		
	}
	
	public void keyPressed() { 
		if (surface.key == 'c') {	
			if (board == null) return;
//			board.add(new Queen(0, 0, board, true));
//			boardPost.getReference().setValueAsync(boardPost);
//			boardPost.updateGrid();
			
//			System.out.println("        " + board.get(0,0).toString());
//			boardPost.getReference().removeValueAsync();		// remove board from database
//			BoardPost post = new BoardPost();
//			post.setBoard(board);
//			board.removeArray();
//			DatabaseReference boardRef = surface.postData(post);
			
			ChangePost post = new ChangePost();
			post.setX(5);
			post.setY(5);
			post.setWhite(false);
			post.setGamePieceName("Queen");
			
			DatabaseReference postRef = surface.postData(post);
			
			
//			DatabaseReference boardPost = surface.postData(this.boardRef);			// error with class mapping???
//			surface.postData(boardPost);
			
//			String ref = boardPost.getReference().toString();
//			ref = ref.substring(0,ref.lastIndexOf("	/"));
//			System.out.println(ref);
			
			// https://chessroyale-e5d70-default-rtdb.firebaseio.com/-N2Nr20CijKMEfScRik1
			
//			System.out.println(board.get(0, 0).getName());
//			System.out.println(boardPost.getReference());
		}
	}
	
	public void pieceAdded(ChangePost post) {
		int x = post.getX();
		int y = post.getY();
		boolean white = post.isWhite();
		String gamePieceName = post.getGamePieceName();
		
		if (gamePieceName == "Bishop") {
			board.add(new Bishop(y, x, board, white));
		} else if (gamePieceName == "Knight") {
			board.add(new Knight(y, x, board, white));
		} else if (gamePieceName == "Pawn") {
			board.add(new Pawn(y, x, board, white));
		} else if (gamePieceName == "Queen") {
			board.add(new Queen(y, x, board, white));
		} else if (gamePieceName == "Rook") {
			board.add(new Rook(y, x, board, white));
		}
		
	}
	
	public void setBoardRef(BoardPost post) {
		this.boardPost = post;
	}
	
	
}