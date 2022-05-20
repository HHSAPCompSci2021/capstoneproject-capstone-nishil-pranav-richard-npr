package gameElements.screens;


import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import com.google.firebase.database.DatabaseReference;

import core.DrawingSurface;
import core.ImageCodes;
import databaseData.ChangePost;
import gameElements.board.*;
import gameElements.pieces.*;
import processing.core.PConstants;
import processing.core.PImage;


/**
 * The screen that holds the game and its elements for the user to interact during a match (placing pieces, seeing enemy pieces, etc)
 * 
 * @author Nishil Anand 
 * @author Pranav Gunhal
 * @version 02
 */
public class ScreenOnlineGame extends Screen implements ActionListener{
	
	private DrawingSurface surface;
	
	private String nameOne;
	private String nameTwo;
	
	private Board board;
	private Rectangle leftKing;
	private Rectangle rightKing;
	
	private Rectangle leftKingHP;
	private Rectangle rightKingHP;
	
	private Player p1, p2, activePlayer;
	private String activePiece;
	
	private Rectangle leftEnergy;
	private Rectangle rightEnergy;
	
//	private Rectangle leftPFP;
//	private Rectangle rightPFP;
	
	private boolean gameInProgress;
	private boolean white;
	
	private final int x = 1200;
	private final int y = 600;
	
	private final int boardX = x/2-323;
	private final int boardY = y/4+14;
	private final int boardWidth = 500;
	private final int boardHeight = 400;
	private final int MAX_KING_HP = 100;
	private PImage blackKing, whiteKing;
	
	private Timer timer;
	private int time;
	
	
	/**
	 * Constructs a new ScreenLocalGame
	 * 
	 * @param surface the DrawingSurface
	 */
	public ScreenOnlineGame(DrawingSurface surface) {
		super(1200,600);
		this.surface = surface;
		
		 leftKing = new Rectangle(x/2-390,y/2+50,100,400);
		rightKing = new Rectangle(x/2+210,y/2+50,100,400);
		//x/2-290
		 leftKingHP = new Rectangle(x/2-390,y/2+50,50,20);
		rightKingHP = new Rectangle(x/2+210,y/2+50,50,20);
		
		board = surface.getBoard();
		p1 = new Player(board, true);
		p2 = new Player(board, false);
		
		leftEnergy = new Rectangle(x/2-557,y/2+50,50,20);
		rightEnergy = new Rectangle(x/2+397,y/2+50,50,20);
		
//		leftPFP = new Rectangle(x/2-405,y/2-275+(94/2),94,94);
//		rightPFP = new Rectangle(x/2+405,y/2-275+(94/2),94,94);
		activePlayer = p1;
		
		gameInProgress = true;
		
		timer = new Timer(1000, this);
	}
	
	/**
	 * Draws the elements of the game onto the processing window.
	 */
	public void draw() {
		
//		System.out.println(surface.getGameReference());
		
		if (gameInProgress) {
			drawGame();
		} else {
			drawVictory();
		}
	}
	
	
	private void drawGame() {
		
		// DO STUFF
		int oneEnergy = p1.getEnergy();
		int twoEnergy = p2.getEnergy();
		
		
		// DRAW STUFF
		surface.pushStyle();
		surface.background(32, 42, 68);
		
		surface.textSize(15);
		surface.fill(255);
		surface.imageMode(PConstants.CENTER);
		surface.rectMode(PConstants.CORNER);
		board = surface.getBoard();
		
		surface.rectMode(PConstants.CENTER);
		surface.imageMode(PConstants.CENTER);
		showBox(leftKing);
		showBox(rightKing);
		whiteKing = surface.getImages().get(ImageCodes.WHITE_CASTLE);
		blackKing = surface.getImages().get(ImageCodes.BLACK_CASTLE);
		surface.image(whiteKing, x/2-290, y/2-50, 75, 188);
		surface.image(blackKing, x/2+310, y/2-50, 75, 188);
		
		surface.image(whiteKing, x/2-290, y/2+175, 75, 188);
		surface.image(blackKing, x/2+310, y/2+175, 75, 188);
		
//		 leftKing = new Rectangle(x/2-390,y/2+50,100,400);
//		rightKing = new Rectangle(x/2+210,y/2+50,100,400);
		
		showTextButton(leftKingHP, board.getKingHealth(true) + "/" + MAX_KING_HP, false);
		showTextButton(rightKingHP, board.getKingHealth(false) + "/" + MAX_KING_HP, false);
		
		board.draw(surface, boardX, boardY, boardWidth, boardHeight);
		
		surface.textSize(15);
        float tempX = x/2-385, tempY = y/2+50-200+(102*0)+(94/2);
        for(int i = 0; i < p1.getCards().size() && i < 5; i++) {
        	Card c = p1.getCards().get(i);
        	c.draw(surface, tempX, tempY, 75, 75);
        	tempY+=90;
        }

        tempX = x/2+415;
        tempY = y/2+50-200+(102*0)+(94/2);
        for(int i = 0; i < p2.getCards().size() && i < 5; i++) {
        	Card c = p2.getCards().get(i);
        	c.draw(surface, tempX, tempY, 75, 75);
        	tempY+=90;
        }
		
		showTextButton(leftEnergy, oneEnergy + "/10");
		showTextButton(rightEnergy, twoEnergy + "/10");
		
//		showBox(leftPFP);
//		showBox(rightPFP);
		
		surface.textSize(25);
		surface.fill(255);
		surface.textAlign(PConstants.LEFT);
		if(nameOne != null)
			surface.text(nameOne, x/2-340, y/2-220);
		surface.textAlign(PConstants.RIGHT);
		if(nameTwo != null)
			surface.text(nameTwo, x/2+340, y/2-220);
		String s = "";
		if(activePlayer.equals(p1)) {
			s = "White's Turn";
		} else if(activePlayer.equals(p2)) {
			s = "Black's Turn";
		}
//		s+="\n" + (180-time);
		surface.textAlign(PConstants.CENTER);
		surface.text(s, x/2, 100);
		if (activePiece != null) {
			if(activePlayer.equals(p1)) {
				surface.rectMode(PConstants.CORNER);
				surface.fill(100, 0, 0, 200);
				surface.rect(boardX + boardWidth/2-boardWidth/15, boardY-14, boardWidth/2 + boardWidth/15, boardHeight);
				surface.fill(255);
				surface.rectMode(PConstants.CENTER);
			}
			else {
				surface.rectMode(PConstants.CORNER);
				surface.fill(100, 0, 0, 200); 
				int x;
				surface.rect(boardX-boardWidth/15, boardY-14, boardWidth/2 + boardWidth/15, boardHeight);
				surface.fill(255);
				surface.rectMode(PConstants.CENTER);
			}
			surface.textSize(15);
			surface.text("Selected " + activePiece, x/2, 120);
		}
		surface.textSize(25);
		surface.text((180-time), x/2, 580);
		
		surface.fill(32, 42, 68);
		surface.rectMode(PConstants.CORNER);
		surface.noStroke();

		if(activePlayer.equals(p1)) {
			surface.rect(x/2+365, 0, surface.width+200, surface.height+200);
		} else if(activePlayer.equals(p2)) {
			surface.rect(0, 0, x/2-345, surface.height+200);
		}
		
		
		surface.popStyle();
		
	}
	
	private void drawVictory() {
		
		surface.pushStyle();
		surface.background(255);
		surface.imageMode(PConstants.CENTER);
		surface.textAlign(PConstants.CENTER, PConstants.CENTER);
		
		surface.image(surface.getImages().get(ImageCodes.BACKGROUND), 1200/2, 600/2, 1200, 600);
		
		surface.tint(255, (float)(255*(3.0/5.0)));
		float squareY = 600/2-20;
		float squareWidth = 1200-(200*2);
		surface.image(surface.getImages().get(ImageCodes.BLACK_SQUARE), 1200/2, 600/2-20, squareWidth, 600-(250*2));
		
		surface.fill(255);
		
		
		int textSize = 60;
		String text = "";
		if (activePlayer == null) {
			text = "DRAW";
		} else if (activePlayer.equals(p1)) {		// player1 (white) won
			surface.textSize(40);
			surface.text("VICTORY!", 1200/2, 200);
			text = nameTwo + " has won!";
		} else if(activePlayer.equals(p2)){	// player2 (black) won
			surface.textSize(40);
			surface.text("VICTORY!", 1200/2, 200);
			text = nameOne + " has won!";
		}
		while (surface.textWidth(text) >= squareWidth) {
			textSize -= 5;
			surface.textSize(textSize);
		}
		surface.text(text, x/2, squareY);
		
		surface.popStyle();
		
	}
	
	/**
	 * Called when a mouse is pressed. 
	 * If a card is right clicked and it's that player's turn, selects that piece. Starts the timer if it's not started yet.
	 * If a piece is selected and there is a left click on the board, places that piece in the board.
	 */
	public void mousePressed() {
		
		Point click = surface.actualCoordinatesToAssumed(new Point(surface.mouseX, surface.mouseY)); 
		
		if (surface.mouseButton == PConstants.RIGHT) { //right button is clicked
			float tempX;
			float tempY =  y/2+50-200+(102*0)+(94/2);
			
			if(activePlayer.equals(p1)) {
				tempX = x/2-485;
			} else {
				tempX = x/2+315;
			}
			
			if (white && activePlayer.equals(p2)) {					// if the player with this screen is white but it's black's turn
				return;
			} else if (!white && activePlayer.equals(p1)) {			// if the player with this screen is black but it's white's turn
				return;
			}
			
	        for(int i = 0; i < activePlayer.getCards().size() && i < 5; i++) {
	        	Card c = activePlayer.getCards().get(i);
	        	if(c.isPointInside(click.x, click.y, tempX, tempY, 75, 75)) { 
	        		if(c.getEnergy() > activePlayer.getEnergy()) { //clicked on the Card, but doesn't have enough energy
	        			System.err.println("could not click on " + c.getPiece());
	        			return;
	        		}
	        		activePiece = c.getPiece();
	        		System.out.println("clicked " + activePiece);
	        		return;
	        	}
	        	tempY+=90;
	        }
	        
		} else {
			Point loc = board.clickToIndex(click, boardX, boardY, boardWidth, boardHeight); 
			if(loc != null && activePiece != null && board.get(loc.x, loc.y) == null) { 
				if(activePlayer.equals(p1)) { //white
					if(loc.x >= board.getWidth()/2) {
						System.err.println("CANNOT PLACE ON ENEMY'S SIDE!!!");
						return;
					}
				} else if(activePlayer.equals(p2)) {
					if(loc.x <= board.getWidth()/2) {
						System.err.println("CANNOT PLACE ON ENEMY'S SIDE!!!");
						return;
					}
				}
				
				// post the new piece
				ChangePost post = new ChangePost();
				post.setX(loc.x);
				post.setY(loc.y);
				post.setWhite(activePlayer.isWhite());
				post.setGamePieceName(activePiece);
				
				DatabaseReference postRef = surface.postData(post, surface.getGameReference());
				
				
//				board.play();
			}
		}
	}
	
	/**
	 * Called whenever a new piece is added to the board.
	 * @param post the post from the database representing the new piece.
	 */
	public void pieceAdded(ChangePost post) {
		
		// get data
		int x = post.getX();
		int y = post.getY();
		int cost;
		boolean white = post.isWhite();
		String gamePieceName = post.getGamePieceName();
		
		// start the time if not started already (so the game begins when the first piece is placed)
		if (time == 0) {
			timer.start();
		}
		
		// console log for debugging
		System.out.println(String.format("pieceAdded (%d, %d)", x, y));
		
		// add piece to board and update the energy "cost" value based on how much the added piece costs
		if (gamePieceName.matches("Bishop")) {
			board.add(new Bishop(y, x, board, white));
			cost = 3;						// TODO: make these not hard coded if extra time
		} else if (gamePieceName.matches("Knight")) {
			board.add(new Knight(y, x, board, white));
			cost = 2;
		} else if (gamePieceName.matches("Pawn")) {
			board.add(new Pawn(y, x, board, white));
			cost = 1;
		} else if (gamePieceName.matches("Queen")) {
			board.add(new Queen(y, x, board, white));
			cost = 5;
		} else if (gamePieceName.matches("Rook")) {
			board.add(new Rook(y, x, board, white));
			cost = 4;
		} else {
			System.err.println("Unknown piece " + gamePieceName);
			for (StackTraceElement v: Thread.currentThread().getStackTrace()) {
			    System.err.println("    " + v);
			}
			return;
		}
		
		// subtract the cost from energy, use a turn, and set activePiece to null
		activePlayer.useEnergy(cost);
		if (activePlayer.equals(p1)) {
			activePlayer = p2;
		} else {
			activePlayer = p1;
		}
		activePiece = null;
	}
	
	/**
	 * If enter/return is pressed the current player's turn is skipped.
	 */
	public void keyPressed() {
		if (surface.key == PConstants.ENTER || surface.key == PConstants.RETURN) {
			skipTurn();
		}
	}
	
	/**
	 * Sets the player's names.
	 * 
	 * @param nameOne name of player one
	 * @param nameTwo name of player two
	 */
	public void setNames(String nameOne, String nameTwo) {
		this.nameOne = nameOne;
		this.nameTwo = nameTwo;
	}
	
	
	private void checkGameOver() {
		int gameOver = board.checkGameOver();
		
		if (gameOver == 0) {			// game is not over
			return;
		} else if (gameOver == 1) {		// black won
			System.out.println("white won");
			gameInProgress = false;
			activePlayer = p1;
		} else if (gameOver == 2) {		// white won
			System.out.println("black won");
			gameInProgress = false;
			activePlayer = p2;
		} 
	}
	
	/**
	 * Every second:
	 * - updates this.time
	 * - calls this.board.play()
	 * - checks for the game being over
	 * 
	 * Also adds energy every 4 seconds.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {		// called every second
		time++;
		board.play();
		this.checkGameOver();
		if (gameInProgress) {
			if(time > 180) {
				// draw the game
				activePlayer = null;
				gameInProgress = false;
			}
			if (time % 7 == 0) { // adds 1 energy every 7 sec
				p1.addEnergy(1);
				p2.addEnergy(1);
			}
		}
	}
	
	private void showTextButton(Rectangle rectangle, String buttonText, boolean border) {
		if (border)
			showBox(rectangle, border);
		
		surface.stroke(0);
		surface.fill(0);
		
		surface.textAlign(PConstants.CENTER);
		surface.text(buttonText, rectangle.x, rectangle.y+5);
	}
	
	private void showBox(Rectangle rectangle, boolean border) {
		if (border) {
			surface.stroke(0);
		} else {
			surface.stroke(255);
		}
		surface.fill(255);
		surface.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 4);		// 4 is how round the edges is
		surface.fill(0);
		
	}
	
	private void showTextButton(Rectangle rectangle, String buttonText) {
		showTextButton(rectangle, buttonText, true);
	}
	
	private void showBox(Rectangle rectangle) {
		showBox(rectangle, true);
	}
	
	private void skipTurn() {
		if (activePlayer == null  || !gameInProgress) return;
		if (activePlayer.equals(p1)) {
			activePlayer = p2;
		} else if (activePlayer.equals(p2)) {
			activePlayer = p1;
		} else {
			return;
		}
		activePiece = null;
//		board.play();
	}

	public void setWhite(boolean white) {
		this.white = white;
	}

}

