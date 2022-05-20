package gameElements.screens;




import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DatabaseReference;

import core.DrawingSurface;
import core.ImageCodes;
import databaseData.UserPost;
import processing.core.PApplet;
import processing.core.PConstants;


public class ScreenOnlineNameCreate extends Screen {
	
	private DrawingSurface surface;

	private StringBuffer boxText;
	private Rectangle nameCreationBox;
	private boolean boxSelected;
	
	private DatabaseReference queueFolder;
	
	public ScreenOnlineNameCreate(DrawingSurface surface, DatabaseReference queueFolder) {
		super(1200,600);
		this.surface = surface;
		
		this.boxText = new StringBuffer();
		this.nameCreationBox = new Rectangle(1200/2-100,600/2-50,175,50);
		this.boxSelected = false;
		this.queueFolder = queueFolder;
	}
	
	
	public void draw() {
		// loading screen if firebase stuff has not loaded in yet
		if (surface.getI() == 0) {
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
	
	public void drawLoading() {
		
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
				UserPost user = addPlayerToQueue(playerName);
//				surface.player = user;
				surface.setPlayerName(playerName);
				surface.switchScreen(ScreenSwitcher.SCREEN4);
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
	
	private UserPost addPlayerToQueue(String playerName) {
		UserPost user = new UserPost(playerName);
		queueFolder.push().setValueAsync(user);
		return user;
//		DatabaseReference pushedPostRef = surface.postData(user, queueFolder);
//		String playerID = pushedPostRef.getKey();
//		user.setPlayerID(playerID);
//		Map<String, Object> updates = new HashMap<String, Object>();
//		updates.put("playerID", playerID);
//		pushedPostRef.updateChildrenAsync(updates);
		
	}
	
}

