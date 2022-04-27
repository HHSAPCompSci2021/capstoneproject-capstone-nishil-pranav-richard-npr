
import java.awt.Color;
import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import processing.core.PApplet;
import processing.core.PConstants;


public class DrawingSurface extends PApplet {
	
	// Drawing stuff
	private static final int DRAWING_WIDTH = 800, DRAWING_HEIGHT = 600;
	
	// Database stuff
	private DatabaseReference ref;
	
	private int i;
	
	public DrawingSurface() {
		
		// DATABASE SETUP
		FileInputStream refreshToken;
		try {

			refreshToken = new FileInputStream("chessroyale-e5d70-firebase-adminsdk-7r4i3-3384c877b4.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(refreshToken))
					.setDatabaseUrl("https://chessroyale-e5d70-default-rtdb.firebaseio.com/")
					.build();

			FirebaseApp.initializeApp(options);
			ref = FirebaseDatabase.getInstance().getReference();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		i += 1;
		if (i == 60*10) {
			i = 0;
			clearAllData();
		}
		
	}
	
	public void mousePressed() {
		String path = "-N0gtrG-WI24DbU-XtL8";
		DatabaseReference postRef = ref.child(path);
		DatabaseReference pushedPostRef = postData(new MessagePost("Hello, world!"), postRef);
//		System.out.println(pushedPostRef.getKey());
		
	}
	
	/**
	 * Posts the given data in the main parent folder.
	 * 
	 * @param data data to post
	 * @return reference to the post created
	 */
	private DatabaseReference postData(Post data) {
		return postData(data, ref);
	}
	
	/**
	 * Posts the given data in the parent folder.
	 * 
	 * @param data data to post
	 * @param location reference to where to post the data. will post in the main parent folder if null
	 * @return reference to the post created
	 */
	private DatabaseReference postData(Post data, DatabaseReference location) {
		if (location == null) location = ref;
		DatabaseReference postRef = location.push();
		postRef.setValueAsync(data);
		return postRef;
	}
	
	/**
	 * Deletes all data stored in the database (main parent folder).
	 */
	private void clearAllData() {
		ref.setValueAsync(null);
	}
	
	/**
	 * Deletes all data stored in location (including nested values).
	 * 
	 * @param location reference to where to delete data
	 */
	private void clearData(DatabaseReference location) {
		if (location == null) return;
		location.setValueAsync(null);
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
