package gameElements.screens;


import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import core.DrawingSurface;
import processing.core.PApplet;
import processing.core.PConstants;


public class ScreenGame extends Screen {
	
	private DrawingSurface surface;
	
	private Rectangle board;
	private Rectangle leftKing;
	private Rectangle rightKing;
	
	private Rectangle leftCardOne;
	private Rectangle leftCardTwo;
	private Rectangle leftCardThree;
	private Rectangle leftCardFour;

	private Rectangle rightCardOne;
	private Rectangle rightCardTwo;
	private Rectangle rightCardThree;
	private Rectangle rightCardFour;
	
	private Rectangle leftEnergy;
	private Rectangle rightEnergy;
	
	private Rectangle leftPFP;
	private Rectangle rightPFP;
	
	// (total height-((number of cards-1)*spacing))/number of cards
	// (400-(3*8))/4
	// = 94
	
	private final int x = 1200;
	private final int y = 600;
	
	public ScreenGame(DrawingSurface surface) {
		super(1200,600);
		this.surface = surface;
		
		board = new Rectangle(x/2+0,y/2+50,700,400);
		 leftKing = new Rectangle(x/2-300,y/2+50,100,400);
		rightKing = new Rectangle(x/2+300,y/2+50,100,400);
		
		  leftCardOne = new Rectangle(x/2-405,y/2+50-200+(102*0)+(94/2),94,94);
		  leftCardTwo = new Rectangle(x/2-405,y/2+50-200+(102*1)+(94/2),94,94);
		leftCardThree = new Rectangle(x/2-405,y/2+50-200+(102*2)+(94/2),94,94);
		 leftCardFour = new Rectangle(x/2-405,y/2+50-200+(102*3)+(94/2),94,94);
		 
		  rightCardOne = new Rectangle(x/2+405,y/2+50-200+(102*0)+(94/2),94,94);
		  rightCardTwo = new Rectangle(x/2+405,y/2+50-200+(102*1)+(94/2),94,94);
		rightCardThree = new Rectangle(x/2+405,y/2+50-200+(102*2)+(94/2),94,94);
		 rightCardFour = new Rectangle(x/2+405,y/2+50-200+(102*3)+(94/2),94,94);
		 
		 leftEnergy = new Rectangle(x/2-507,y/2+50,50,20);
		rightEnergy = new Rectangle(x/2+507,y/2+50,50,20);
		
		 leftPFP = new Rectangle(x/2-405,y/2-275+(94/2),94,94);
		rightPFP = new Rectangle(x/2+405,y/2-275+(94/2),94,94);
	}
	
	
	public void draw() {
		
		// DO STUFF
		int oneEnergy = 3;		// how much energy does player 1 have
		int twoEnergy = 5;		// how much energy does player 2 have
		String oneName = "Nishil";
		String twoName = "Pranav";
		
		
		// DRAW STUFF
		surface.pushStyle();
		surface.background(255,255,255);
		
		surface.textSize(15);
		surface.fill(255);
		surface.rectMode(PConstants.CENTER);
		
		showBox(board);
		showBox(leftKing);
		showBox(rightKing);
		
		showBox(leftCardOne);
		showBox(leftCardTwo);
		showBox(leftCardThree);
		showBox(leftCardFour);
		
		showBox(rightCardOne);
		showBox(rightCardTwo);
		showBox(rightCardThree);
		showBox(rightCardFour);
		
		showTextButton(leftEnergy, oneEnergy + "/10");
		showTextButton(rightEnergy, twoEnergy + "/10");
		
		showBox(leftPFP);
		showBox(rightPFP);
		
		surface.textSize(25);
		surface.textAlign(PConstants.LEFT);
		surface.text(oneName, x/2-340, y/2-220);
		surface.textAlign(PConstants.RIGHT);
		surface.text(twoName, x/2+340, y/2-220);
		
		surface.popStyle();
		
	}
	
	
	public void mousePressed() {
//		Point p = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
//		
//		if (button.contains(p))
//			surface.switchScreen(ScreenSwitcher.SCREEN2);
//		
//		if (multiplayerButton.contains(p))
//			surface.switchScreen(ScreenSwitcher.SCREEN3);
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

