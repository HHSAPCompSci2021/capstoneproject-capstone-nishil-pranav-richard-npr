package gameElements.screens;




import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DatabaseReference;

import core.DrawingSurface;
import databaseData.UserPost;
import processing.core.PApplet;
import processing.core.PConstants;


public class ScreenLocalNameCreate extends Screen {
	
	private DrawingSurface surface;
	private ScreenLocalGame screenLocalGame;
	
	private StringBuffer boxText;
	private Rectangle nameCreationBox;
	
	private String player1; 
	private String player2;
	private boolean boxSelected;
	
	
	public ScreenLocalNameCreate(DrawingSurface surface, ScreenLocalGame screenLocalGame) {
		super(1200,600);
		this.surface = surface;
		
		this.boxText = new StringBuffer();
		this.nameCreationBox = new Rectangle(1200/2-75,600/2-50,200,50);
		this.boxSelected = false;
		this.screenLocalGame = screenLocalGame;
	}
	
	
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
	
	public void mousePressed() {
		Point p = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
		
		if (nameCreationBox.contains(p)) {
			boxSelected = true;
		} else {
			boxSelected = false;
			boxText = new StringBuffer();		// clear out old text once user clicks off
		}
		
	}

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

