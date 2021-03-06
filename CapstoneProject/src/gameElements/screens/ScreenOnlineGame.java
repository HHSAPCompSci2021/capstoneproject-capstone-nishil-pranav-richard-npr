package gameElements.screens;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import core.DrawingSurface;
import core.ImageCodes;
import databaseData.ChangePost;
import gameElements.board.*;
import gameElements.pieces.*;
import processing.core.PConstants;
import processing.core.PImage;


/**
 * This Screen holds the game and its elements for the user to interact with during a match (placing pieces, seeing enemy pieces, etc)
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
	
	private boolean gameInProgress;
	private boolean white;
	
	private final int x = 1200;
	private final int y = 600;
	
	private final int boardX = x/2-223;
	private final int boardY = y/4+14;
	private final int boardWidth = 500;
	private final int boardHeight = 400;
	private final int MAX_KING_HP = 200;
	private PImage blackKing, whiteKing;
	
	private Timer timer;
	private int time;
	
	
	/**
	 * Constructs a new ScreenOnline
	 * 
	 * @param surface the DrawingSurface
	 */
	public ScreenOnlineGame(DrawingSurface surface) {
		super(1200,600);
		this.surface = surface;
		
		 leftKing = new Rectangle(x/2-290,y/2+50,100,400);
		rightKing = new Rectangle(x/2+310,y/2+50,100,400);
		//x/2-290
		 leftKingHP = new Rectangle(x/2-290,y/2+50,50,20);
		rightKingHP = new Rectangle(x/2+310,y/2+50,50,20);
		
		board = surface.getBoard();
		p1 = new Player(board, true);
		p2 = new Player(board, false);
		
		leftEnergy = new Rectangle(x/2-457,y/2+50,50,20);
		rightEnergy = new Rectangle(x/2+497,y/2+50,50,20);
		
		if (white) {
			activePlayer = p1;
		} else {
			activePlayer = p2;
		}
		
		gameInProgress = true;
		
		timer = new Timer(1000, this);
	}
	
	
	/**
	 * Draws the elements of the game onto the processing window.
	 */
	public void draw() {
		if (gameInProgress) {
			drawGame();
		} else {
			drawVictory();
		}
	}
	
	private void drawGame() {
		
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
		
		showTextButton(leftEnergy, p1.getEnergy() + "/10");
		showTextButton(rightEnergy, p2.getEnergy() + "/10");
		
		surface.textSize(25);
		surface.fill(255);
		surface.textAlign(PConstants.LEFT);
		
		if (nameOne != null) 
			surface.text(nameOne, x/2-340, y/2-220);
		surface.textAlign(PConstants.RIGHT);
		if (nameTwo != null)
			surface.text(nameTwo, x/2+340, y/2-220);
		
		surface.textAlign(PConstants.CENTER);
		if (activePiece != null) {
			if (activePlayer.equals(p1)) {
				surface.rectMode(PConstants.CORNER);
				surface.fill(100, 0, 0, 100);
				surface.rect(boardX + boardWidth/2-boardWidth/15, boardY-14, boardWidth/2 + boardWidth/15, boardHeight);
				surface.fill(255);
				surface.rectMode(PConstants.CENTER);
			} else if (activePlayer.equals(p2)){
				surface.rectMode(PConstants.CORNER);
				surface.fill(100, 0, 0, 100); 
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

		if (white) {
			surface.rect(x/2+365, 0, surface.width+200, surface.height+200);
		} else {
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
			
			if (white) {
				tempX = x/2-385;
			} else {
				tempX = x/2+415;
			}
			
	        for(int i = 0; i < activePlayer.getCards().size() && i < 5; i++) {
	        	Card c = activePlayer.getCards().get(i);
	        	if(c.isPointInside(click.x, click.y, tempX, tempY, 75, 75)) { 
	        		if(c.getEnergy() > activePlayer.getEnergy()) { //clicked on the Card, but doesn't have enough energy
	        			System.err.println("could not click on " + c.getPiece());
	        			return;
	        		}
	        		activePiece = c.getPiece();
	        		return;
	        	}
	        	tempY+=90;
	        }
	        
		} else {
			Point loc = board.clickToIndex(click, boardX, boardY, boardWidth, boardHeight); 
			if(loc != null && activePiece != null && board.get(loc.y, loc.x) == null) { 
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
				
				surface.postData(post, surface.getGameReference());
				
			}
		}
	}
	
	/**
	 * Adds a new piece to the board.
	 * 
	 * @param post the Post from the database representing the new piece.
	 */
	public void addPiece(ChangePost post) {
		
		// get data
		int x = post.getX();
		int y = post.getY();
		int cost;							// TODO: make cost not hard coded if extra time
		boolean white = post.isWhite();
		String gamePieceName = post.getGamePieceName();
		
		// start the time if not started already (so the game begins when the first piece is placed)
		if (time == 0) {
			timer.start();
		}
		
		// add piece to board and update the energy "cost" value based on how much the added piece costs
		if (gamePieceName.matches("Bishop")) {
			board.add(new Bishop(y, x, board, white));
			cost = 3;
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
		
		// subtract the cost from energy and set activePiece to null
		if (activePlayer.equals(this.playerFromWhite(white)))
			activePlayer.useEnergy(cost);
		activePiece = null;
		
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
	
	/**
	 * Sets the white field to white and adjusts activePlayer
	 * 
	 * @param white if the player with this screen is white
	 */
	public void setWhite(boolean white) {
		System.err.println("white set to " + white);
		if (white) {
			activePlayer = p1;
		} else {
			activePlayer = p2;
		}
		this.white = white;
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
				p1.addEnergy(1);
				p2.addEnergy(1);

		}
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
	
	private Player playerFromWhite(boolean white) {
		if (white) {
			return p1;
		} else {
			return p2;
		}
	}

}

