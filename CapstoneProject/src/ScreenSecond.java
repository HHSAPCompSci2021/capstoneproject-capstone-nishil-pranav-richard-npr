


import java.awt.event.*;

import databaseData.Post;
import processing.core.PApplet;
import processing.core.PConstants;


public class ScreenSecond extends Screen {
	
	private int x, y;
	private String message;
	
	private DrawingSurface surface;
	
	public ScreenSecond(DrawingSurface surface) {
		super(800,600);
		this.surface = surface;
		
		x = 600;
		y = 100;
		
		message = "";
	}
	
	public void draw() {
		
		// Draw stuff
		
		surface.pushStyle();
		
		surface.background(255);   // Clear the screen with a white background
		surface.stroke(0);     // Set line drawing color to white
		surface.noFill();

		surface.rect(x,y,30,30);
		
		surface.fill(0);
		surface.text("Move: Arrow keys",10,30);
		surface.text("Menu & clear database: `",10,50);
		surface.text("Send to databse: Enter",10,70);
		
		surface.text(message,10,120);

		surface.popStyle();

		
		
		// Change stuff

		if (surface.isPressed(KeyEvent.VK_LEFT))
			x -= 5;
		if (surface.isPressed(KeyEvent.VK_RIGHT))
			x += 5;
		if (surface.isPressed(KeyEvent.VK_UP))
			y -= 5;
		if (surface.isPressed(KeyEvent.VK_DOWN))
			y += 5;
		
	}
	
	public void keyPressed() {
		if (surface.key == '`') {
			surface.clearAllData();
			surface.switchScreen(ScreenSwitcher.SCREEN1);
		} else if (surface.keyCode == KeyEvent.VK_ENTER) {
			surface.postData(new Post(message));
			message = "";
		} else if (surface.keyCode == KeyEvent.VK_BACK_SPACE) {
			message = message.substring(0, message.length()-1);
		} else {
			message += surface.key;
		}
	}
	
	
}