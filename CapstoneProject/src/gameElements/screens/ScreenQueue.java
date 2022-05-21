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
import core.DrawingSurface.DatabaseChangeListener;
import databaseData.BoardPost;
import databaseData.IntegerPost;
import databaseData.UserPost;
import processing.core.PConstants;

public class ScreenQueue extends Screen {
	
	private DrawingSurface surface;
	private DatabaseReference ref;
	private ArrayList<UserPost> queue;
	private ScreenOnlineGame gameScreen;
	
	private Rectangle button;
	
//	private boolean white;
	private boolean firstLoop;
	private boolean switched;
	private DatabaseReference roomCreated;
	
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
		switched = false;
		
	}
	
	
	public void draw() {
		
		// CHECK FOR OPPONENTS
		
		String str = "";
		str = Integer.toString(queue.size());
		
		if (switched) {
			System.err.println("switch failed?");
		}
		
		// make a new game
		if (firstLoop) {
			firstLoop = false;
			if (queue.size() > 0) {		// if there is someone already waiting in queue
//				System.out.println("quyeuedsadas: " + queue.size());
				
				BoardPost board = new BoardPost();
				DatabaseReference boardRef = surface.postData(board);
//				gameScreen.setNames("a", "b");
//				gameScreen.setBoardRef(boardRef);
//				gameScreen.setBoardRef(gameCreated);
				
				DatabaseReference gameRef = ref.child(Integer.toString(surface.getI()));
				surface.postData(new IntegerPost(0), gameRef);
				
				surface.addChildEventListener(gameRef);
				surface.setGameReference(gameRef);
				gameScreen.setWhite(true);
				gameScreen.setNames(surface.getPlayerName(), surface.getQueue().get(0).getPlayerName());
				surface.switchScreen(ScreenSwitcher.SCREEN8);
				switched = true;
				return;
			}
		}
		
		
		// connect to an existing game
		if (roomCreated != null && queue.size() > 0 && queue.size() % 2 == 0) {
//			gameScreen.setNames("a", "b");
//			gameScreen.setBoardRef(gameCreated);
//			gameScreen.setBoardRef(gameCreated);
			
//			String whiteName = gameCreated.getWhiteName();
//			String blackName = gameCreated.getBlackName();
			
			// folder for game replicating	
			DatabaseReference gameRef = roomCreated;
			surface.addChildEventListener(gameRef);
			surface.setGameReference(gameRef);
			gameScreen.setWhite(false);
//			NamePost name = new NamePost(whiteName, blackName);
//			surface.postData(name, gameRef);
//			gameScreen.setNames(whiteName, blackName);
			
			gameScreen.setNames(surface.getQueue().get(1).getPlayerName(), surface.getQueue().get(0).getPlayerName());
			
			// remove from queue
			surface.clearData(ref.child("Queue"));				// TODO: clear data when leaving game, keeping updated i value for new players
			surface.switchScreen(ScreenSwitcher.SCREEN8);
			switched = true;
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
	
	public void roomCreated(DataSnapshot post) {
		System.out.println("ROOM CREATED");
		String ref = post.getRef().toString();
		String name = ref.substring(ref.lastIndexOf("/")+1);
		this.roomCreated = this.ref.child(name);
	}
	
	private void showButton(Rectangle rectangle, String buttonText) {
		surface.fill(255);
		surface.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 4);		// 4 is how round the edges is
		surface.fill(0);
		surface.textAlign(PConstants.CENTER);
		surface.text(buttonText, rectangle.x+rectangle.width/2, rectangle.y+rectangle.height/2+6);
	}
	
}
