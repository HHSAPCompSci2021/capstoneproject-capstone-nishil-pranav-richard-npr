

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

/**
 * 
 * OK: this also extends PApplet, but setup() isnt being called
 * likely bcoz no JFrame is calling it like main()
 * 
 * ALSO loadImage() just will not work in any class
 * Im done w/ this 
 * */

public class PImageCreator extends PApplet {
	
	// Drawing/screen stuff
	private final String fileSeparator = System.getProperty("file.separator");
	public float ratioX, ratioY;
	private static final int DRAWING_WIDTH = 800, DRAWING_HEIGHT = 600;
	private Screen activeScreen;
	private ArrayList<Screen> screens;
	private ArrayList<Integer> keys;
	private String playerName;
	
	// Database stuff
	private int i;
	
	public PImageCreator(String s) {
		//setup();
		
		// SCREEN SETUP
		screens = new ArrayList<Screen>();
		keys = new ArrayList<Integer>();
		
//		ScreenMenu screen1 = new ScreenMenu(this);
//		screens.add(screen1);
//		
//		ScreenSecond screen2 = new ScreenSecond(this);
//		screens.add(screen2);
//		
//		ScreenNameCreate screen3 = new ScreenNameCreate(this, queueRef);
//		screens.add(screen3);
//		
//		ScreenQueue screen4 = new ScreenQueue(this, ref);
//		screens.add(screen4);
		
	//	activeScreen = screens.get(0);
		
		// OTHER
		playerName = null;
		
	}
	
	public void setup() {
		System.out.println("WHYYYYYY + PIMAGE");
	}

	public void draw() {
//		background(255);
//		
//		currentDrawing.draw(this);
//		
//		for (int i = 0; i < possibleColors.length; i++) {
//			fill(possibleColors[i].getRGB());
//			rect(colorRects[i].x,colorRects[i].y,colorRects[i].width,colorRects[i].height);
//		}
//		
//		fill(255);
//		rect(clearButton.x, clearButton.y, clearButton.width, clearButton.height);
//		
//		fill(0);
//		textAlign(PConstants.CENTER, PConstants.CENTER);
//		text("CLEAR",clearButton.x, clearButton.y, clearButton.width, clearButton.height);
		
		// clears data every 10 seconds
//		i += 1;
//		if (i == 60*1) {
//			i = 0;
//			clearAllData();
//		}
		
		// draw the screen
		ratioX = (float)width/activeScreen.DRAWING_WIDTH;
		ratioY = (float)height/activeScreen.DRAWING_HEIGHT;
		scale(ratioX, ratioY);
		activeScreen.draw();
		
	}
	
	public PImage getImage() {
		System.out.println("RETURN HERE DRAWINGSURFACE");
		return null;
	}
	
	public void keyPressed() {
		keys.add(keyCode);
//		if (key == 'h') {
//			postData(new MessagePost("h"));
//		}
		if (activeScreen == screens.get(ScreenSwitcher.SCREEN2) && key != CODED) {
			((ScreenSecond) activeScreen).keyPressed();
		} else if (activeScreen == screens.get(ScreenSwitcher.SCREEN3)) {
			((ScreenNameCreate) activeScreen).keyPressed();
		}
	}

	public void keyReleased() {
		while(keys.contains(keyCode))
			keys.remove(new Integer(keyCode));
	}

	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}
	
	
	public void mousePressed() {
//		String path = "Folder";
//		DatabaseReference postRef = ref.child(path);
//		DatabaseReference pushedPostRef = postData(new MessagePost("Hello, world!"), postRef);
//		System.out.println(pushedPostRef.getKey());
		activeScreen.mousePressed();
	}
	
	
	public Point assumedCoordinatesToActual(Point assumed) {
		return new Point((int)(assumed.getX()*ratioX), (int)(assumed.getY()*ratioY));
	}

	public Point actualCoordinatesToAssumed(Point actual) {
		return new Point((int)(actual.getX()/ratioX) , (int)(actual.getY()/ratioY));
	}

	public void switchScreen(int i) {
		activeScreen = screens.get(i);
	}


	
	/**
	 * Sets the player's name. Note that the name can only be set once.
	 * 
	 * @param name player name
	 * @pre the player's name has not been set yet. If it has already been set, method will return without doing anything.
	 */
	public void setPlayerName(String name) {
		if (playerName != null) return;
		playerName = name;
	}

}
