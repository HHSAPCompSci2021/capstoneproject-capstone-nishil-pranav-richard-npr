package gameElements.screens;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import core.DrawingSurface;
import processing.core.PConstants;


/**
 * This Screen is used for the user to enter names before they play a local game
 * 
 * @author Nishil Anand
 */
public class ScreenLocalNameCreate extends Screen {
	
	private DrawingSurface surface;
	private ScreenLocalGame screenLocalGame;
	
	private StringBuffer boxText;
	private Rectangle nameCreationBox;
	
	private String player1; 
	private String player2;
	private boolean boxSelected;
	
	
	/**
	 * Constructs a new ScreenLocalNameCreate using a DrawingSurface
	 * 
	 * @param surface the DrawingSurface to draw with
	 * @param screenLocalGame the ScreenLocalGame that this class is providing names for
	 */
	public ScreenLocalNameCreate(DrawingSurface surface, ScreenLocalGame screenLocalGame) {
		super(1200,600);
		this.surface = surface;
		
		this.boxText = new StringBuffer();
		this.nameCreationBox = new Rectangle(1200/2-75,600/2-50,200,50);
		this.boxSelected = false;
		this.screenLocalGame = screenLocalGame;
	}
	
	
	/**
	 * Draws the elements needed to enter a name onto the Processing window.
	 */
	public void draw() {
		
		surface.pushStyle();
		surface.background(255,255,255);
		
		surface.textSize(20);
		
		String str;
		if (player1 == null) {
			str = "Enter Player 1 Name";
		} else {
			str = "Enter Player 2 Name";
		}
		if (boxSelected) {
			str = boxText.toString();
		}
		showButton(nameCreationBox, str);
		
		surface.popStyle();
		
	}
	
	/**
	 * Called when a mouse is pressed. 
	 * Used for box selection.
	 */
	public void mousePressed() {
		
		Point p = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
		
		if (nameCreationBox.contains(p)) {
			boxSelected = true;
		} else {
			boxSelected = false;
			boxText = new StringBuffer();		// clear out old text once user clicks off
		}
		
	}
	
	/**
	 * Called when a key is pressed.
	 * Used for entering the name and confirming the name (enter)
	 */
	public void keyPressed() {
		char key = surface.key;
		int ascii = (int)key;
		if (boxSelected) {
			if (surface.keyCode == KeyEvent.VK_ENTER) {
				String playerName = boxText.toString();
				if (player1 == null) {
					player1 = playerName;
					boxText = new StringBuffer();
					boxSelected = false;
				} else {
					player2 = playerName;
					screenLocalGame.setNames(player1, player2);
					surface.switchScreen(ScreenSwitcher.SCREEN5);
				}
			} else if (surface.keyCode == KeyEvent.VK_BACK_SPACE) {
				int index = boxText.length()-1;
				if (index < 0) return;				// prevent exception from backspacing with nothing there
				boxText.deleteCharAt(index);
			} else if (ascii >= 32 && ascii <= 126) {
				boxText.append(key);
			}
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
