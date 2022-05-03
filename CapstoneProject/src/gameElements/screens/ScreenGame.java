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
	
	private final int x = 800;
	private final int y = 600;
	
	public ScreenGame(DrawingSurface surface) {
		super(800,600);
		this.surface = surface;
		
		board = new Rectangle(x/2+0,y/2+50,500,400);
	}
	
	
	public void draw() {
		
		surface.rectMode(surface.CENTER);
		
		// MOUSE STUFF FOR HELPING DRAW
		System.out.println(surface.mouseX + "       " + surface.mouseY);
		
		
		// DRAW STUFF
		surface.pushStyle();
		surface.background(255,255,255);
		
		surface.textSize(20);
		
		showBox(board);
		
		surface.popStyle();
		
//		System.out.println(button.y + "         " + surface.mouseX + "      " + surface.mouseY);
		
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
	
	private void showButton(Rectangle rectangle, String buttonText) {
		surface.fill(255);
		surface.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 4);		// 4 is how round the edges is
		surface.fill(0);
		surface.textAlign(PConstants.CENTER);
		surface.text(buttonText, rectangle.x+rectangle.width/2, rectangle.y+rectangle.height/2+6);
	}
	
	private void showBox(Rectangle rectangle) {
		surface.fill(255);
		surface.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 4);		// 4 is how round the edges is
		surface.fill(0);
	}

}

