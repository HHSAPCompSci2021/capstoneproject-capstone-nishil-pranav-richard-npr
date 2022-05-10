package gameElements.screens;


import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import core.DrawingSurface;
import gameElements.board.Board;
import gameElements.board.Card;
import gameElements.board.Player;
import gameElements.pieces.Bishop;
import gameElements.pieces.GamePiece;
import gameElements.pieces.Pawn;
import processing.core.PApplet;
import processing.core.PConstants;


public class ScreenLocalGame extends Screen {
	
	private DrawingSurface surface;
	
	private String nameOne;
	private String nameTwo;
	
	private Board board;
//	private Rectangle board;
	private Rectangle leftKing;
	private Rectangle rightKing;
	
//	private Rectangle leftCardOne;
//	private Rectangle leftCardTwo;
//	private Rectangle leftCardThree;
//	private Rectangle leftCardFour;
//
//	private Rectangle rightCardOne;
//	private Rectangle rightCardTwo;
//	private Rectangle rightCardThree;
//	private Rectangle rightCardFour;
	private Player p1, p2;
	
	private Rectangle leftEnergy;
	private Rectangle rightEnergy;
	
	private Rectangle leftPFP;
	private Rectangle rightPFP;
	
	// (total height-((number of cards-1)*spacing))/number of cards
	// (400-(3*8))/4
	// = 94
	
	private final int x = 1200;
	private final int y = 600;
	
	public ScreenLocalGame(DrawingSurface surface) {
		super(1200,600);
		this.surface = surface;
		
//		board = new Rectangle(x/2+0,y/2+50,700,400);
		 leftKing = new Rectangle(x/2-300,y/2+50,100,400);
		rightKing = new Rectangle(x/2+300,y/2+50,100,400);
		
//		  leftCardOne = new Rectangle(x/2-405,y/2+50-200+(102*0)+(94/2),94,94);
//		  leftCardTwo = new Rectangle(x/2-405,y/2+50-200+(102*1)+(94/2),94,94);
//		leftCardThree = new Rectangle(x/2-405,y/2+50-200+(102*2)+(94/2),94,94);
//		 leftCardFour = new Rectangle(x/2-405,y/2+50-200+(102*3)+(94/2),94,94);
//		 
//		  rightCardOne = new Rectangle(x/2+405,y/2+50-200+(102*0)+(94/2),94,94);
//		  rightCardTwo = new Rectangle(x/2+405,y/2+50-200+(102*1)+(94/2),94,94);
//		rightCardThree = new Rectangle(x/2+405,y/2+50-200+(102*2)+(94/2),94,94);
//		 rightCardFour = new Rectangle(x/2+405,y/2+50-200+(102*3)+(94/2),94,94);
		board = surface.getBoard();
		p1 = new Player(board, true);
		p2 = new Player(board, false);
		 
		 leftEnergy = new Rectangle(x/2-507,y/2+50,50,20);
		rightEnergy = new Rectangle(x/2+507,y/2+50,50,20);
		
		 leftPFP = new Rectangle(x/2-405,y/2-275+(94/2),94,94);
		rightPFP = new Rectangle(x/2+405,y/2-275+(94/2),94,94);
	}
	
	
	public void draw() {
		
		// DO STUFF
		int oneEnergy = 3;		// how much energy does player 1 have
		int twoEnergy = 5;		// how much energy does player 2 have
		
		
		// DRAW STUFF
		surface.pushStyle();
		surface.background(255,255,255);
		
		surface.textSize(15);
		surface.fill(255);
		surface.rectMode(PConstants.CENTER);
		surface.imageMode(PConstants.CENTER);
		board = surface.getBoard();
		
		board.draw(surface, (x-450)/2, y/2-130, 500, 400);
//		showBox(board);
		showBox(leftKing);
		showBox(rightKing);
		
//		showBox(leftCardOne);
//		showBox(leftCardTwo);
//		showBox(leftCardThree);
//		showBox(leftCardFour);
//		
//		showBox(rightCardOne);
//		showBox(rightCardTwo);
//		showBox(rightCardThree);
//		showBox(rightCardFour);
		
        float tempX = x/2-405, tempY = y/2+50-200+(102*0)+(94/2);
        for(int i = 0; i < p1.getCards().size() && i < 5; i++) {
        	Card c = p2.getCards().get(i);
        	c.draw(surface, tempX, tempY, 75, 75, surface);
        	tempY+=90;
        }
        
        tempX = x/2+405;
        tempY = y/2+50-200+(102*0)+(94/2);
        for(int i = 0; i < p1.getCards().size() && i < 5; i++) {
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
		
		surface.popStyle();
		
//		System.out.println(board.get(0,0));
		
	}
	
	
	public void mousePressed() {
//		Point p = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
//		
//		if (button.contains(p))
//			surface.switchScreen(ScreenSwitcher.SCREEN2);
//		
//		if (multiplayerButton.contains(p))
//			surface.switchScreen(ScreenSwitcher.SCREEN3);
		
//		board.add(new Pawn(0, 0, board, true));
//		board.add(new Pawn(0, 1, board, false));
		//R C Board White
		Point click = new Point(surface.mouseX, surface.mouseY);
		Point loc = board.clickToIndex(click, x, y, surface.getBoard().getWidth(), surface.getBoard().getHeight());
		if(loc != null) {
			
			
			GamePiece g = null;
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

}

