package gameElements.screens;




import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import core.DrawingSurface;
import gameElements.board.Board;
import processing.core.PApplet;
import processing.core.PConstants;
import core.ImageCodes;


public class ScreenMenu extends Screen {
	
	private DrawingSurface surface;
	
	private Rectangle button;
	private Rectangle multiplayerButton;
	private Rectangle localButton;
	
	public ScreenMenu(DrawingSurface surface) {
		super(1200,600);
		this.surface = surface;
		
		button = new Rectangle(1200/2-100,600/2-50,175,50);
		multiplayerButton = new Rectangle(1200/2-100,600/2+50,175,50);
		localButton = new Rectangle(1200/2-100,600/2+150,175,50);
	}
	
	
	public void draw() {
		
		surface.pushStyle();
		//surface.background(255,255,255);
		surface.image(surface.getImages().get(ImageCodes.BACKGROUND), 0, 0, 1200, 600);
		
//		surface.textSize(20);
//		showButton(button, "Test Database");
//		surface.textSize(16);
//		showButton(multiplayerButton, "Find opponent (wip)");
		surface.textSize(18);
		showButton(localButton, "Play with a friend");
		
		surface.popStyle();
		
//		System.out.println(button.y + "         " + surface.mouseX + "      " + surface.mouseY);
		
	}
	
	
	public void mousePressed() {
		Point p = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
		
//		if (button.contains(p))
//			surface.switchScreen(ScreenSwitcher.SCREEN2);
		
//		if (multiplayerButton.contains(p))
//			surface.switchScreen(ScreenSwitcher.SCREEN3);
		
		if (localButton.contains(p)) {
			Board board = new Board();
			surface.setBoard(board);
			surface.switchScreen(ScreenSwitcher.SCREEN6);
		}
			
	}
	
	private void showButton(Rectangle rectangle, String buttonText) {
		surface.fill(255);
		surface.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 4);		// 4 is how round the edges is
		surface.fill(0);
		surface.textAlign(PConstants.CENTER);
		surface.text(buttonText, rectangle.x+rectangle.width/2, rectangle.y+rectangle.height/2+6);
	}
	

}

