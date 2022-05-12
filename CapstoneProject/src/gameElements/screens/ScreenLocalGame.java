package gameElements.screens;


import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import core.DrawingSurface;
import core.ImageCodes;
import gameElements.board.*;
import gameElements.pieces.*;
import processing.core.PConstants;


public class ScreenLocalGame extends Screen implements ActionListener{
	
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
	
	private Rectangle leftPFP;
	private Rectangle rightPFP;
	
	
	// (total height-((number of cards-1)*spacing))/number of cards
	// (400-(3*8))/4
	// = 94
	
	private boolean gameInProgress;
	private int whiteKingHP;
	private int blackKingHP;
	
	private final int x = 1200;
	private final int y = 600;
	
	private final int boardX = 375-(50/2);
	private final int boardY = y/2-130-(40/2);
	private final int boardWidth = 500;
	private final int boardHeight = 400;
	private final int MAX_KING_HP = 100;
	private Timer timer;
	private int time;
	
	
	public ScreenLocalGame(DrawingSurface surface) {
		super(1200,600);
		this.surface = surface;
		
		 leftKing = new Rectangle(x/2-300,y/2+50,100,400);
		rightKing = new Rectangle(x/2+300,y/2+50,100,400);
		
		 leftKingHP = new Rectangle(x/2-300,y/2+50,50,20);
		rightKingHP = new Rectangle(x/2+300,y/2+50,50,20);
		
		board = surface.getBoard();
		p1 = new Player(board, true);
		p2 = new Player(board, false);
		 
		 leftEnergy = new Rectangle(x/2-507,y/2+50,50,20);
		rightEnergy = new Rectangle(x/2+507,y/2+50,50,20);
		
		 leftPFP = new Rectangle(x/2-405,y/2-275+(94/2),94,94);
		rightPFP = new Rectangle(x/2+405,y/2-275+(94/2),94,94);
		activePlayer = p1;
		
		gameInProgress = true;
		whiteKingHP = MAX_KING_HP;
		blackKingHP = MAX_KING_HP;
		
		timer = new Timer(1000, this);
	}
	
	public void draw() {
		if (gameInProgress) {
			drawGame();
		} else {
			drawVictory();
		}
	}
	
	
	public void drawGame() {
		
		// DO STUFF
		int oneEnergy = p1.getEnergy();
		int twoEnergy = p2.getEnergy();
		
		
		// DRAW STUFF
		surface.pushStyle();
		surface.background(198, 46, 46);
		
		surface.textSize(15);
		surface.fill(255);
		surface.imageMode(PConstants.CENTER);
		surface.rectMode(PConstants.CORNER);
		board = surface.getBoard();
		
		board.draw(surface, boardX, boardY, boardWidth, boardHeight);
		surface.rectMode(PConstants.CENTER);
		surface.imageMode(PConstants.CENTER);
		showBox(leftKing);
		showBox(rightKing);
		showTextButton(leftKingHP, whiteKingHP + "/" + MAX_KING_HP, false);
		showTextButton(rightKingHP, blackKingHP + "/" + MAX_KING_HP, false);
		
        float tempX = x/2-405, tempY = y/2+50-200+(102*0)+(94/2);
        for(int i = 0; i < p1.getCards().size() && i < 5; i++) {
        	Card c = p1.getCards().get(i);
        	c.draw(surface, tempX, tempY, 75, 75);
        	tempY+=90;
        }
        
        tempX = x/2+405;
        tempY = y/2+50-200+(102*0)+(94/2);
        for(int i = 0; i < p2.getCards().size() && i < 5; i++) {
        	Card c = p2.getCards().get(i);
        	c.draw(surface, tempX, tempY, 75, 75);
        	tempY+=90;
        }
		
		showTextButton(leftEnergy, oneEnergy + "/10");
		showTextButton(rightEnergy, twoEnergy + "/10");
		
		showBox(leftPFP);
		showBox(rightPFP);
		
		surface.textSize(25);
		surface.textAlign(PConstants.LEFT);
		surface.text(nameOne, x/2-340, y/2-220);
		surface.textAlign(PConstants.RIGHT);
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
			surface.textSize(15);
			surface.text("Selected " + activePiece, x/2, 120);
		}
		surface.textSize(25);
		surface.text((180-time), x/2, 580);
		
		surface.popStyle();
		
	}
	
	public void drawVictory() {
		
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
		
		surface.textSize(40);
		surface.text("VICTORY!", 1200/2, 200);
		
		int textSize = 60;
		String text = "";
		if (activePlayer.equals(p1)) {		// player1 (white) won
			text = nameOne + " has won!";
		} else {							// player2 (black) won
			text = nameTwo + " has won!";
		}
		while (surface.textWidth(text) >= squareWidth) {
			textSize -= 5;
			surface.textSize(textSize);
		}
		surface.text(text, x/2, squareY);
		
		surface.popStyle();
		
	}
	
	
	public void mousePressed() {
		
		
		Point click= surface.actualCoordinatesToAssumed(new Point(surface.mouseX, surface.mouseY)); 
		
		if(surface.mouseButton == PConstants.RIGHT) { //right button is clicked
			//start the time if not started already
			//so the game begins when the first player chooses their piece
			if(time == 0) {
				timer.start();
			}
			
			float tempX;
			float tempY = 197-75/2;
			
			if(activePlayer.equals(p1)) {
				tempX = 195-75/2;
			} else {
				tempX = x/2+405-75/2;
			}
			
	        for(int i = 0; i < activePlayer.getCards().size() && i < 5; i++) {
	        	Card c = activePlayer.getCards().get(i);
	        	if(c.isPointInside(click.x, click.y, tempX, tempY, 75, 75)) { 
	        		if(c.getEnergy() > activePlayer.getEnergy()) { //clicked on the Card, but don't have enough energy
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
			if(loc != null && activePiece != null) { 
				GamePiece p = gpFromString(activePiece, activePlayer.isWhite(), loc.x, loc.y);
				board.add(p);
				//TODO remove
				if(activePlayer.equals(p1)) {
					activePlayer = p2;
				} else {
					activePlayer = p1;
				}
				activePiece = null;
				board.play();
			}
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
	
	/**
	 * Damages the king. If the king dies, prints that the game has ended and updates gameInProgress.
	 * 
	 * @param dmg how much damage to inflict
	 * @param white true to damage white king, false to damage black king
	 */
	public void damageKing(int dmg, boolean white) {
		if (white) {
			whiteKingHP -= dmg;
			if (whiteKingHP <= 0) {
				whiteKingHP = 0;
				System.out.println("white won");
				gameInProgress = false;
				activePlayer = p1;
			}
		} else {
			blackKingHP -= dmg;
			if (blackKingHP <= 0) {
				blackKingHP = 0;
				System.out.println("black won");
				gameInProgress = false;
				activePlayer = p2;
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
	
	private GamePiece gpFromString(String name, boolean white, int col, int row) {
		switch(name) {
		case "Queen": return new Queen(row, col, board, white);
		case "Knight": return new Knight(row, col, board, white);
		case "Bishop": return new Bishop(row, col, board, white);
		case "Rook": return new Rook(row, col, board, white);
		case "Pawn": return new Pawn(row, col, board, white);
		}
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		time++;
		if(time > 180) {
			gameInProgress = false;
		}
		if(time % 4 == 0) { //TODO change the modulus for different time
			//currently adds 1 energy every 4 sec
			p1.addEnergy(1);
			p2.addEnergy(2);
		}
		
	}

}

