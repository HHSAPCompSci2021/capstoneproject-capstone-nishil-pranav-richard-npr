package gameElements.screens;


import java.awt.Point;
import java.awt.Rectangle;
//import java.util.ArrayList;
import core.DrawingSurface;
import gameElements.board.*;
import gameElements.pieces.*;
import processing.core.PConstants;


public class ScreenLocalGame extends Screen {
	
	private DrawingSurface surface;
	
	private String nameOne;
	private String nameTwo;
	
	private Board board;
	private Rectangle leftKing;
	private Rectangle rightKing;
	
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
	
	
	public ScreenLocalGame(DrawingSurface surface) {
		super(1200,600);
		this.surface = surface;
		
		 leftKing = new Rectangle(x/2-300,y/2+50,100,400);
		rightKing = new Rectangle(x/2+300,y/2+50,100,400);
		
		board = surface.getBoard();
		p1 = new Player(board, true);
		p2 = new Player(board, false);
		 
		 leftEnergy = new Rectangle(x/2-507,y/2+50,50,20);
		rightEnergy = new Rectangle(x/2+507,y/2+50,50,20);
		
		 leftPFP = new Rectangle(x/2-405,y/2-275+(94/2),94,94);
		rightPFP = new Rectangle(x/2+405,y/2-275+(94/2),94,94);
		activePlayer = p1;
		
		gameInProgress = true;
		whiteKingHP = 100;
		blackKingHP = 100;
	}
	
	
	public void draw() {
		
		// DO STUFF
		int oneEnergy = 3;		// how much energy does player 1 have
		int twoEnergy = 5;		// how much energy does player 2 have
		
		
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
		
        float tempX = x/2-405, tempY = y/2+50-200+(102*0)+(94/2);
        for(int i = 0; i < p1.getCards().size() && i < 5; i++) {
        	Card c = p1.getCards().get(i);
        	c.draw(surface, tempX, tempY, 75, 75, surface);
        	tempY+=90;
        }
        
        tempX = x/2+405;
        tempY = y/2+50-200+(102*0)+(94/2);
        for(int i = 0; i < p2.getCards().size() && i < 5; i++) {
        	Card c = p2.getCards().get(i);
        	c.draw(surface, tempX, tempY, 75, 75, surface);
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
		String s ="";
		if(activePlayer.equals(p1)) {
			s="Player1";
		} else if(activePlayer.equals(p2)) {
			s="Player2";
		}
		surface.text(s, surface.width/2-50, 100);
		
		surface.popStyle();
		
//		surface.rectMode(PConstants.CORNER);
//		surface.rect(x/2+405-75/2, 197-75/2+90+90+90+90, 75, 75);
//		System.out.println(197-75/2+90+90+90+90);
		
		
	}
	
	
	public void mousePressed() {
		if(surface.mouseButton == PConstants.RIGHT) { //right button is clicked
			if(activePlayer.equals(p1)) {
				float tempX = 195-75/2, tempY = 197-75/2;
		        for(int i = 0; i < p1.getCards().size() && i < 5; i++) {
		        	Card c = p1.getCards().get(i);
		        //	System.out.println(tempX + "     " + tempY);
		        //	System.out.println(surface.mouseX + "     " + surface.mouseY);
		        	if(c.isPointInside(surface.mouseX, surface.mouseY, tempX, tempY, 75, 75)) { 
		        		activePiece = c.getPiece();
		        		return;
		        	}
		        	tempY+=90;
		        }
			} else if(activePlayer.equals(p2)) {
		       float tempX = x/2+405-75/2;
		       float tempY = 197-75/2;
		        for(int i = 0; i < p2.getCards().size() && i < 5; i++) {
		        	Card c = p2.getCards().get(i);
		        //	System.out.println(tempX + "     " + tempY);
		        //	System.out.println(surface.mouseX + "     " + surface.mouseY);
		        	if(c.isPointInside(surface.mouseX, surface.mouseY, tempX, tempY, 75, 75)) { 
		        		activePiece = c.getPiece();
		        		return; 
		        	} 
		        	tempY+=90; 
		        } 
			}
	         
		} else { 
			Point click = new Point(surface.mouseX, surface.mouseY); 
			click = surface.actualCoordinatesToAssumed(click); 
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
			}
		} else {
			blackKingHP -= dmg;
			if (blackKingHP <= 0) {
				blackKingHP = 0;
				System.out.println("black won");
				gameInProgress = false;
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
	
	private GamePiece gpFromString(String name, boolean white, int row, int col) {
		switch(name) {
		case "Queen": return new Queen(row, col, board, white);
		case "Knight": return new Knight(row, col, board, white);
		case "Bishop": return new Bishop(row, col, board, white);
		case "Rook": return new Rook(row, col, board, white);
		case "Pawn": return new Pawn(row, col, board, white);
		}
		return null;
	}

}

