package gameElements.screens;

/**
 * Abstract class representing a screen. When adding a screen, remember to:
 * - make it extend this class.
 * - add it to the Screen setup in the DrawingSurface constructor
 * - add it to ScreenCodes
 * 
 * @author Nishil Anand
 * @author john_shelby
 */
public abstract class Screen {
	
	/**
	 * Represents the width and height that this Screen is to be drawn with
	 * */
	public final int DRAWING_WIDTH, DRAWING_HEIGHT;
	
	/**
	 * Constructs a new Screen with the specified width and height
	 * @param width The width of this Screen
	 * @param height The height of this Screen
	 * */
	public Screen(int width, int height) {
		this.DRAWING_WIDTH = width;
		this.DRAWING_HEIGHT = height;
	}
	
	/**
	 * Sets up this Screen by performing actions that only need to be done once
	 * */
	public void setup() {
		
	}
	
	/**
	 * Draws this Screen
	 * */
	public void draw() {
		
	}
	
	/**
	 * Performs an action when the mouse is pressed
	 * */
	public void mousePressed() {
		
	}
	
	/**
	 * Performs an action when a key is pressed
	 * */
	public void keyPressed() {
		
	}
	
}
