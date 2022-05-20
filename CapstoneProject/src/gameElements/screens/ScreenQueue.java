package gameElements.screens;


import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.SwingUtilities;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import core.DrawingSurface;
import databaseData.BoardPost;
import databaseData.UserPost;
import processing.core.PConstants;

public class ScreenQueue extends Screen {
	
	private DrawingSurface surface;
	private DatabaseReference ref;
	private ArrayList<UserPost> queue;
	private ScreenOnlineGame gameScreen;
	
	private Rectangle button;
	
	private int i;
	private boolean updated;
//	private boolean white;
	private boolean firstLoop;
	private BoardPost gameCreated;
	
	public ScreenQueue(DrawingSurface surface, DatabaseReference ref, ArrayList<UserPost> queue, ScreenOnlineGame gameScreen) {
		super(1200,600);
		this.surface = surface;
		this.ref = ref;
		this.queue = queue;
		this.gameScreen = gameScreen;
		this.firstLoop = true;
		
		/*
		 * when there are 2 players in the queue, the one that joined first is white
		 */
//		if (queue.size() > 0) {
//			white = false;
//		} else {
//			white = true;
//		}
		
		button = new Rectangle(1200/2-100,600/2-50,200,50);
	}
	
	
	public void draw() {
		
		// CHECK FOR OPPONENTS
		
		String str = "";
		str = Integer.toString(queue.size());
		
		
		if (firstLoop) {
			firstLoop = false;
			if (queue.size() > 0) {		// if there is someone already waiting in queue
				BoardPost board = new BoardPost();
				DatabaseReference boardRef = surface.postData(board);
//				gameScreen.setNames("a", "b");
//				gameScreen.setBoardRef(boardRef);
//				gameScreen.setBoardRef(gameCreated);
				gameScreen.setWhite(true);
				surface.switchScreen(ScreenSwitcher.SCREEN8);
				return;
			}
		}
		
		
		if (gameCreated != null && queue.size() == 2) {
//			gameScreen.setNames("a", "b");
//			gameScreen.setBoardRef(gameCreated);
//			gameScreen.setBoardRef(gameCreated);
			gameScreen.setWhite(false);
			surface.switchScreen(ScreenSwitcher.SCREEN8);
			return;
		}
		
		
		
		
		// SCREEN STUFF
		surface.pushStyle();
		surface.background(255,255,255);
		
		surface.textSize(20);
		
//		String str;
//		if (white) {
//			str = "white";
//		} else {
//			str = "black";
//		}
//		showButton(button, str);
		showButton(button, str);
		
		surface.popStyle();
		
	}
	
	public void queueUpdated() {
		updated = true;
//		System.out.println("updatedd");
//		white = false;
	}
	
	public void gameCreated(BoardPost post) {
		gameCreated = post;
	}
	
	private void showButton(Rectangle rectangle, String buttonText) {
		surface.fill(255);
		surface.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 4);		// 4 is how round the edges is
		surface.fill(0);
		surface.textAlign(PConstants.CENTER);
		surface.text(buttonText, rectangle.x+rectangle.width/2, rectangle.y+rectangle.height/2+6);
	}
	
}

