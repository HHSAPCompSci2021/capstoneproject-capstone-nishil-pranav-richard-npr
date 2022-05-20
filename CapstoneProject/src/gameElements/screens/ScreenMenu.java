package gameElements.screens;




import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import core.DrawingSurface;
import gameElements.board.Board;
import processing.core.PApplet;
import processing.core.PConstants;
import core.ImageCodes;

/**
 * 
 * Screen representing the menu that players first see when they join the game.
 * From the menu, they can choose to view instructions, play online, or play locally (with a friend).
 * 
 * @author Nishil Anand
 *
 */
public class ScreenMenu extends Screen {
	
	private DrawingSurface surface;
	
	private Rectangle onlineButton;
	private Rectangle localButton;
	private Rectangle instructionsButton;
	
	
	/**
	 * Constructs a new Screen Menu with a DrawingSurface
	 * @param surface the DrawingSurface this class uses to draw
	 */
	public ScreenMenu(DrawingSurface surface) {
		super(1200,600);
		this.surface = surface;
		
		onlineButton = new Rectangle(1200/2-100,600/2-150,175,50);
		instructionsButton = new Rectangle(1200/2-100,600/2-50,175,50);
		localButton = new Rectangle(1200/2-100,600/2+50,175,50);
	}
	
	
	/**
	 * Draws the menu
	 */
	public void draw() {
		surface.image(surface.getImages().get(ImageCodes.BACKGROUND), 0, 0, 1200, 600);
		
		surface.textSize(18);
		showButton(onlineButton, "Play online");
		showButton(instructionsButton, "Instructions");
		showButton(localButton, "Play with a friend");
		
		surface.popStyle();
	}
	
	/**
	 * Called whenever the mouse is pressed.
	 * Used to identify if the user has clicked on a button.
	 */
	public void mousePressed() {
		Point p = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
		
		if (onlineButton.contains(p))
			surface.switchScreen(ScreenSwitcher.SCREEN3);
		
		if (instructionsButton.contains(p)) {
			surface.switchScreen(ScreenSwitcher.SCREEN7);
		}
		
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

