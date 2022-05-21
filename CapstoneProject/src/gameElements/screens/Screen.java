package gameElements.screens;

/**
 * Abstract class representing a screen. When adding a screen, remember to:
 * - make it extend this class.
 * - add it to the Screen setup in the DrawingSurface constructor
 * - add it to ScreenSwitcher
 * 
 * @author Nishil Anand
 * @author john_shelby
 */
public abstract class Screen {
	
	public final int DRAWING_WIDTH, DRAWING_HEIGHT;
	
	public Screen(int width, int height) {
		this.DRAWING_WIDTH = width;
		this.DRAWING_HEIGHT = height;
	}
	
	public void setup() {
		
	}
	
	public void draw() {
		
	}
	
	public void mousePressed() {
		
	}
	
	public void keyPressed() {
		
	}
	
}
