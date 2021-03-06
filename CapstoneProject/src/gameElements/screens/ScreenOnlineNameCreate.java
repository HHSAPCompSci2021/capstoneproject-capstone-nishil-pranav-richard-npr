package gameElements.screens;

import com.google.firebase.database.DatabaseReference;
import databaseData.UserPost;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import processing.core.PConstants;
import core.DrawingSurface;
import core.ImageCodes;


/**
 * This Screen is used for the user to enter their name before they queue up for an online game.
 * 
 * @author Nishil Anand
 */
public class ScreenOnlineNameCreate extends Screen {
	
	private DrawingSurface surface;

	private StringBuffer boxText;
	private Rectangle nameCreationBox;
	private boolean boxSelected;
	
	private DatabaseReference queueFolder;
	
	
	/**
	 * Constructs a new ScreenOnlineNameCreate using a DrawingSurface
	 * 
	 * @param surface the DrawingSurface to draw with
	 * @param queueFolder the DatabaseReference to where to post the UserPost to once they are ready to join the queue
	 */
	public ScreenOnlineNameCreate(DrawingSurface surface, DatabaseReference queueFolder) {
		super(1200,600);
		this.surface = surface;
		
		this.boxText = new StringBuffer();
		this.nameCreationBox = new Rectangle(1200/2-100,600/2-50,175,50);
		this.boxSelected = false;
		this.queueFolder = queueFolder;
	}
	
	
	/**
	 * Draws the elements needed to enter a name onto the Processing window.
	 */
	public void draw() {
		
		// loading screen if firebase stuff has not loaded in yet
		if (!surface.isLoaded()) {
			drawLoading();
			return;
		}
		
		surface.pushStyle();
		surface.background(255,255,255);
		
		surface.textSize(20);
		
		String str = "Enter Name";
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
				addPlayerToQueue(playerName);
				surface.setPlayerName(playerName);
				surface.switchScreen(ScreenCodes.SCREEN4);
			} else if (surface.keyCode == KeyEvent.VK_BACK_SPACE) {
				int index = boxText.length()-1;
				if (index < 0) return;				// prevent exception from backspacing with nothing there
				boxText.deleteCharAt(index);
			} else if (ascii >= 32 && ascii <= 126) {
				boxText.append(key);
			}
		}
	}
	
	private void drawLoading() {
		
		int width = 1200;
		int height = 600;
		
		surface.pushStyle();
		surface.background(255);
		surface.imageMode(PConstants.CENTER);
		surface.textAlign(PConstants.CENTER, PConstants.CENTER);
		
		surface.image(surface.getImages().get(ImageCodes.BACKGROUND), width/2, height/2, width, height);
		
		surface.tint(255, (float)(255*(3.0/5.0)));
		surface.image(surface.getImages().get(ImageCodes.BLACK_SQUARE), width/2, height/2-20, width-(200*2), height-(225*2));
		
		surface.fill(255);
		
		surface.textSize(50);
		surface.text("LOADING", width/2, height/2-20);
		
		surface.popStyle();
		
	}
	
	private void showButton(Rectangle rectangle, String buttonText) {
		surface.fill(255);
		surface.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 4);		// 4 is how round the edges is
		surface.fill(0);	
		surface.textAlign(PConstants.CENTER);
		surface.text(buttonText, rectangle.x+rectangle.width/2, rectangle.y+rectangle.height/2+6);
	}
	
	private UserPost addPlayerToQueue(String playerName) {
		UserPost user = new UserPost(playerName);
		DatabaseReference userRef = queueFolder.push();
		userRef.setValueAsync(user);
		surface.setInQueue(userRef);
		return user;
	}
	
}
