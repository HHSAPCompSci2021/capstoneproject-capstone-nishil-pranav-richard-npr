

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import databaseData.MessagePost;
import databaseData.Post;
import databaseData.UserPost;
import processing.core.PApplet;
import processing.core.PConstants;


public class DrawingSurface extends PApplet {
	
	// Drawing/screen stuff
	public float ratioX, ratioY;
	private static final int DRAWING_WIDTH = 800, DRAWING_HEIGHT = 600;
	private Screen activeScreen;
	private ArrayList<Screen> screens;
	private ArrayList<Integer> keys;
	private String playerName;
	
	// Database stuff
	private DatabaseReference ref;
	
	private int i;
	
	public DrawingSurface() {
		
		// DATABASE SETUP
		FileInputStream refreshToken;
		DatabaseReference queueRef = null;
		try {

			refreshToken = new FileInputStream("chessroyale-e5d70-firebase-adminsdk-7r4i3-3384c877b4.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(refreshToken))
					.setDatabaseUrl("https://chessroyale-e5d70-default-rtdb.firebaseio.com/")
					.build();

			FirebaseApp.initializeApp(options);
			ref = FirebaseDatabase.getInstance().getReference();
			ref.addChildEventListener(new DatabaseChangeListener());
			
			queueRef = ref.child("Queue");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// SCREEN SETUP
		screens = new ArrayList<Screen>();
		keys = new ArrayList<Integer>();
		
		ScreenMenu screen1 = new ScreenMenu(this);
		screens.add(screen1);
		
		ScreenSecond screen2 = new ScreenSecond(this);
		screens.add(screen2);
		
		ScreenNameCreate screen3 = new ScreenNameCreate(this, queueRef);
		screens.add(screen3);
		
		ScreenQueue screen4 = new ScreenQueue(this, ref);
		screens.add(screen4);
		
		activeScreen = screens.get(0);
		
		
		// OTHER
		playerName = null;
		
	}

	public void settings() {
		
	}
	
	public void setup() {
//		currentDrawing = new Drawing(DRAWING_WIDTH, DRAWING_HEIGHT, this);
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
	
	public void keyPressed() {
		keys.add(keyCode);
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
	
	public void mouseMoved() {
		activeScreen.mouseMoved();
	}
	
	public void mouseDragged() {
		activeScreen.mouseDragged();
	}
	
	public void mouseReleased() {
		activeScreen.mouseReleased();
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
	 * Posts the given data in the main parent folder.
	 * 
	 * @param data data to post
	 * @return reference to the post created
	 */
	public DatabaseReference postData(Post data) {
		return postData(data, ref);
	}
	
	/**
	 * Posts the given data in the parent folder.
	 * 
	 * @param data data to post
	 * @param location reference to where to post the data. will post in the main parent folder if null
	 * @return reference to the post created
	 */
	public DatabaseReference postData(Post data, DatabaseReference location) {
		if (location == null) location = ref;
		DatabaseReference postRef = location.push();
		postRef.setValueAsync(data);
		return postRef;
	}
	
	/**
	 * Deletes all data stored in the database (main parent folder).
	 */
	public void clearAllData() {
		ref.setValueAsync(null);
	}
	
	/**
	 * Deletes all data stored in location (including nested values).
	 * 
	 * @param location reference to where to delete data
	 */
	public void clearData(DatabaseReference location) {
		if (location == null) return;
		location.setValueAsync(null);
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
	
	/**
	 * 
	 * Handles all changes to the database reference. Because Firebase uses a separate thread than most other processes we're using (both Swing and Processing),
	 * we need to have a strategy for ensuring that code is executed somewhere besides these methods.
	 * 
	 * @author john_shelby
	 *
	 */
	public class DatabaseChangeListener implements ChildEventListener {

		private ConcurrentLinkedQueue<Runnable> tasks;
		
		public DatabaseChangeListener() {   // This threading strategy will work with Processing programs. Just use this code inside your PApplet.
			tasks = new ConcurrentLinkedQueue<Runnable>();
			DrawingSurface.this.registerMethod("post", this);
		}
		
		
		public void post() {
			while (!tasks.isEmpty()) {
				Runnable r = tasks.remove();
				r.run();
			}
		}
		
		
		@Override
		public void onCancelled(DatabaseError arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		/**
		 * This method is called automatically every time something is added to the database (by you or
		 * someone else). It is also called at the beginning of the program for all existing database posts. 
		 */
		public void onChildAdded(DataSnapshot dataSnapshot, String arg1) {
			
			tasks.add(new Runnable() {

				@Override
				public void run() {
					Post post = dataSnapshot.getValue(Post.class);
//					currentDrawing.addDotSet(post.dots, new Color(post.r,post.g,post.b));
				}
				
			});
		}

		@Override
		public void onChildChanged(DataSnapshot arg0, String arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onChildMoved(DataSnapshot arg0, String arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onChildRemoved(DataSnapshot arg0) {
			tasks.add(new Runnable() {

				@Override
				public void run() {
//					currentDrawing.clear();
				}
				
			});
			
		}

	}


}
