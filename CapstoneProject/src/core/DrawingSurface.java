package core;

import java.awt.Point;
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

import databaseData.*;
import gameElements.board.Board;
import gameElements.screens.*;
import processing.core.PApplet;
import processing.core.PImage;


/**
 * Represents a PApplet that holds all assets for this program.
 * 
 * @author Nishil Anand
 */
public class DrawingSurface extends PApplet {
	
	// Drawing/screen stuff
	private final String fileSeparator = System.getProperty("file.separator");
	public float ratioX, ratioY;
	private Screen activeScreen;
	private ArrayList<Screen> screens;
	private ArrayList<Integer> keys;
	private ArrayList<PImage> images;
	
	// Database stuff 
	private DatabaseReference ref;
	private DatabaseReference gameRef;
	private DatabaseReference inQueue;		// the player running this game. used for if they leave
	
	private ArrayList<UserPost> queue;
	private String playerName;
	private Board board;
	private boolean loaded;
	private int i;			// used for gameIDs
	
	
	/**
	 * Constructs a new DrawingSurface, setting up fields, the database, and screens.
	 */
	public DrawingSurface() {
		
		// SETUP NORMAL FIELDS
		playerName = null;
		queue = new ArrayList<UserPost>();
		i = 0;
		
		
		// DATABASE SETUP
		FileInputStream refreshToken;
		DatabaseReference queueRef = null;
		try {

			refreshToken = new FileInputStream("dataBaseKey.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(refreshToken))
					.setDatabaseUrl("https://chessroyale-e5d70-default-rtdb.firebaseio.com/")
					.build();

			FirebaseApp.initializeApp(options);
			ref = FirebaseDatabase.getInstance().getReference();
			queueRef = ref.child("Queue");
			
			ref.addChildEventListener(new DatabaseChangeListener());
			queueRef.addChildEventListener(new DatabaseChangeListener());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// SCREEN SETUP
		screens = new ArrayList<Screen>();
		keys = new ArrayList<Integer>();
		images = new ArrayList<PImage>();
		
		ScreenMenu screen1 = new ScreenMenu(this);
		screens.add(screen1);
		
		ScreenSecond screen2 = new ScreenSecond(this);
		screens.add(screen2);
		
		ScreenOnlineNameCreate screen3 = new ScreenOnlineNameCreate(this, queueRef);
		screens.add(screen3);
		
		ScreenOnlineGame screen4 = new ScreenOnlineGame(this);
		screens.add(screen4);
		
		ScreenLocalGame screen5 = new ScreenLocalGame(this);
		screens.add(screen5);
		
		ScreenLocalNameCreate screen6 = new ScreenLocalNameCreate(this, screen5);
		screens.add(screen6);
		
		ScreenInstructions screen7 = new ScreenInstructions(this);
		screens.add(screen7);
		
		ScreenQueue screen8 = new ScreenQueue(this, ref, queue, screen4);
		screens.add(screen8);
		
		activeScreen = screens.get(0);
		
	}
	
	/**
	 * Sets up the images.
	 */
	public void setup() {
		
		images.add(ImageCodes.UNKNOWN, loadImage(String.format("Images%sunknown.png", fileSeparator)));
		
		images.add(ImageCodes.BLACK_BISHOP, loadImage(String.format("Images%sChessPieces%sblackSide%sblackBishop.png", fileSeparator, fileSeparator, fileSeparator)));
		images.add(ImageCodes.BLACK_KING, loadImage(String.format("Images%sChessPieces%sblackSide%sblackKing.png", fileSeparator, fileSeparator, fileSeparator)));
		images.add(ImageCodes.BLACK_KNIGHT, loadImage(String.format("Images%sChessPieces%sblackSide%sblackKnight.png", fileSeparator, fileSeparator, fileSeparator)));
		images.add(ImageCodes.BLACK_PAWN, loadImage(String.format("Images%sChessPieces%sblackSide%sblackPawn.png", fileSeparator, fileSeparator, fileSeparator)));
		images.add(ImageCodes.BLACK_QUEEN, loadImage(String.format("Images%sChessPieces%sblackSide%sblackQueen.png", fileSeparator, fileSeparator, fileSeparator)));
		images.add(ImageCodes.BLACK_ROOK, loadImage(String.format("Images%sChessPieces%sblackSide%sblackRook.png", fileSeparator, fileSeparator, fileSeparator)));
		
		images.add(ImageCodes.WHITE_BISHOP, loadImage(String.format("Images%sChessPieces%swhiteSide%swhiteBishop.png", fileSeparator, fileSeparator, fileSeparator)));
		images.add(ImageCodes.WHITE_KING, loadImage(String.format("Images%sChessPieces%swhiteSide%swhiteKing.png", fileSeparator, fileSeparator, fileSeparator)));
		images.add(ImageCodes.WHITE_KNIGHT, loadImage(String.format("Images%sChessPieces%swhiteSide%swhiteKnight.png", fileSeparator, fileSeparator, fileSeparator)));
		images.add(ImageCodes.WHITE_PAWN, loadImage(String.format("Images%sChessPieces%swhiteSide%swhitePawn.png", fileSeparator, fileSeparator, fileSeparator)));
		images.add(ImageCodes.WHITE_QUEEN, loadImage(String.format("Images%sChessPieces%swhiteSide%swhiteQueen.png", fileSeparator, fileSeparator, fileSeparator)));
		images.add(ImageCodes.WHITE_ROOK, loadImage(String.format("Images%sChessPieces%swhiteSide%swhiteRook.png", fileSeparator, fileSeparator, fileSeparator)));
		
		images.add(ImageCodes.BACKGROUND, loadImage(String.format("Images%sbackground.jpg", fileSeparator, fileSeparator, fileSeparator)));
		images.add(ImageCodes.BLACK_SQUARE, loadImage(String.format("Images%sblackSquare.png", fileSeparator, fileSeparator, fileSeparator)));
		
		images.add(ImageCodes.WHITE_CASTLE, loadImage(String.format("Images%sChessPieces%swhiteSide%swhiteCastle.png", fileSeparator, fileSeparator, fileSeparator)));
		images.add(ImageCodes.BLACK_CASTLE, loadImage(String.format("Images%sChessPieces%sblackSide%sblackCastle.png", fileSeparator, fileSeparator, fileSeparator)));
		
		
		Runtime.getRuntime().addShutdownHook(new Thread()  // when the program exits remove the player from queue (if they are in the queue when they left)
			{
			      public void run() {
			    	  if (inQueue != null)
			    		  clearData(inQueue);
			      }
			});
		
	}
	
	/**
	 * Draws the current active screen.
	 */
	public void draw() {
		ratioX = (float)width/activeScreen.DRAWING_WIDTH;
		ratioY = (float)height/activeScreen.DRAWING_HEIGHT;
		scale(ratioX, ratioY);
		activeScreen.draw();
	}
	
	/**
	 * Updates keys (adds it).
	 * Calls the activeScreen's keyPressed() method if needed.
	 */
	public void keyPressed() {
		keys.add(keyCode);
		if (activeScreen == screens.get(ScreenCodes.SCREEN3)) {
			((ScreenOnlineNameCreate) activeScreen).keyPressed();
		} else if (activeScreen == screens.get(ScreenCodes.SCREEN6)) {
			((ScreenLocalNameCreate) activeScreen).keyPressed();
		} else if (activeScreen == screens.get(ScreenCodes.SCREEN5)) {
			((ScreenLocalGame) activeScreen).keyPressed();
		} else if (activeScreen == screens.get(ScreenCodes.SCREEN8)) {
			((ScreenOnlineGame) activeScreen).keyPressed();
		}
	}

	/**
	 * Updates keys (removes it).
	 */
	public void keyReleased() {
		while(keys.contains(keyCode))
			keys.remove(new Integer(keyCode));
	}

	/**
	 * Checks if code is being pressed.
	 * 
	 * @param code the key to check if it's being pressed
	 * @return if the key is being pressed
	 */
	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}
	
	/**
	 * Calls the activeScreen's mousePressed() method
	 */
	public void mousePressed() {
		activeScreen.mousePressed();
	}
	
	/**
	 * Converts the assumed coordinates to actual coordinates
	 * 
	 * @param assumed the assumed coordinates
	 * @return the actual coordinates
	 */
	public Point assumedCoordinatesToActual(Point assumed) {
		return new Point((int)(assumed.getX()*ratioX), (int)(assumed.getY()*ratioY));
	}

	/**
	 * Converts the actual coordinates to assumed coordinates
	 * 
	 * @param actual the actual coordinates
	 * @return the assumed coordinates
	 */
	public Point actualCoordinatesToAssumed(Point actual) {
		return new Point((int)(actual.getX()/ratioX) , (int)(actual.getY()/ratioY));
	}

	/**
	 * Switches the active screen to i
	 * 
	 * @param i the new active screen
	 */
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
	 * Returns the player name of the player who is running this class. May be null.
	 * 
	 * @return the name of this player
	 */
	public String getPlayerName() {
		return playerName;
	}
	
	/**
	 * Returns a list of all loaded images
	 * 
	 * @return a list of all loaded images
	 */
	public ArrayList<PImage> getImages() {
		return images;
	}
	
	/**
	 * Sets the Board to board 
	 * 
	 * @param board the new game board
	 */
	public void setBoard(Board board) {
		this.board = board;
	}
	
	/**
	 * Returns the game board. Could be null.
	 * 
	 * @return the game board
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Returns the database reference to the current game (networking). Could be null.
	 * 
	 * @return the database reference to the current game
	 */
	public DatabaseReference getGameReference() {
		return gameRef;
	}
	
	/**
	 * Sets the database reference to the current game (networking) to gameRef.
	 * 
	 * @param gameRef new database reference to the current game
	 */
	public void setGameReference(DatabaseReference gameRef) {
		this.gameRef = gameRef;
	}
	
	/**
	 * Returns an int to be used for a roomID/gameID. Whenever a new game is created, this value goes up.
	 * This makes it so that two games don't have the same roomID. 
	 * 
	 * @return an int to be used for a new roomID
	 */
	public int getI() {
		return i;
	}
	
	/**
	 * Returns if Firebase has been loaded yet.
	 * It counts as loaded once a single post from the Database has been loaded in.
	 * 
	 * @return if Firebase has been loaded yet
	 */
	public boolean isLoaded() {
		return loaded;
	}
	
	/**
	 * Adds a ChildEventListener to ref
	 * @param ref a DatabaseReference to add the ChildEventListener to
	 */
	public void addChildEventListener(DatabaseReference ref) {
		ref.addChildEventListener(new DatabaseChangeListener());
	}
	
	/**
	 * Tells the ScreenQueue that the queue was updated if it is the active screen
	 */
	public void updatedQueue() {
		//Screen screen = screens.get(7);
		if (this.activeScreen.equals(screens.get(7))) {
			//ScreenQueue queueScreen = (ScreenQueue) screen;
		}
	}
	
	/**
	 * Tells the ScreenQueue that a game was made if it is the active screen
	 * @param dataSnapshot the DataSnapshot of the room
	 */
	public void roomCreated(DataSnapshot dataSnapshot) {
		Screen q = screens.get(7);
		if (this.activeScreen.equals(q)) {
			ScreenQueue screen = (ScreenQueue) q;
			screen.roomCreated(dataSnapshot);
		}
	}
	
	/**
	 * Tells the ScreenOnlineGame that a piece was added if it is the active screen
	 * @param post the post of the piece to add
	 */
	public void pieceAdded(ChangePost post) {
		if (this.activeScreen.equals(screens.get(3))) {
			ScreenOnlineGame screen = (ScreenOnlineGame) screens.get(3);
			screen.addPiece(post);
		}
	}
	
	/** 
	 * Returns an ArrayList of UserPosts with players in the queue.
	 * @return an ArrayList of UserPosts with players in the queue.
	 */
	public ArrayList<UserPost> getQueue() {
		return queue;
	}
	
	/**
	 * Sets the inQueue of this surface to inQueue 
	 * @param inQueue new value for inQueue
	 */
	public void setInQueue(DatabaseReference inQueue) {
		this.inQueue = inQueue;
	}
	
	// runs when something is loaded in
	private void loaded() {
		loaded = true;
	}
	
	/**
	 * 
	 * Handles all changes to the database reference. Because Firebase uses a separate thread than most other processes we're using (both Swing and Processing),
	 * we need to have a strategy for ensuring that code is executed somewhere besides these methods.
	 * 
	 * @author john_shelby
	 * @author Nishil Anand
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
					Post postN = dataSnapshot.getValue(Post.class);
					System.out.println("> add " + postN + postN.postType);
					String postType = postN.postType;
					loaded();
					if (postType != null) {
						if (postType.matches("USER")) {
							UserPost post = dataSnapshot.getValue(UserPost.class);
							queue.add(post);
							updatedQueue();
						} else if (postType.matches("BOARD")) {
							BoardPost post = dataSnapshot.getValue(BoardPost.class);
							System.out.println("    BOARD ADDED: " + post);
							setBoard(new Board());
						} else if (postType.matches("PIECEADDED")) {
							ChangePost post = dataSnapshot.getValue(ChangePost.class);
							System.out.println("    CHANGE: " + post);
							pieceAdded(post);
						}
					} else {
						System.err.println("null post " + dataSnapshot.getRef());
						DatabaseReference postRef = dataSnapshot.getRef();
						String refStr = postRef.toString();										// the reference of the post, such as "https://chessroyale-e5d70-default-rtdb.firebaseio.com/Queue"
						String name = refStr.substring(refStr.lastIndexOf("/") + 1);			// get the name of the post, such as "Queue"
						if (name.equals("Queue")) return;										// only continue if it's not Queue, meaning that it is a game
						int name2 = Integer.parseInt(name);
						i = name2+1;
						roomCreated(dataSnapshot);
					}
				}
				
			});
		}

		@Override
		public void onChildChanged(DataSnapshot arg0, String arg1) {
			Post postN = arg0.getValue(Post.class);
			String postType = postN.postType;
			if (postType != null) {
				if (postType.matches("BOARD")) {
					BoardPost post = arg0.getValue(BoardPost.class);
					System.out.println("    BOARD UPDATED: " + post);
					setBoard(post.getBoard());
				} else if (postType.matches("INT")) {
					//IntegerPost post = arg0.getValue(IntegerPost.class);
				}
			}
		} 

		@Override
		public void onChildMoved(DataSnapshot arg0, String arg1) {
			return;
		}

		@Override
		public void onChildRemoved(DataSnapshot arg0) {
			tasks.add(new Runnable() {
				
				@Override
				public void run() {
					Post postN = arg0.getValue(Post.class);
					System.out.println("> remove " + postN + postN.postType);
					String postType = postN.postType;
					if (postType != null ) {
						if (postType.matches("USER")) {
							UserPost post = arg0.getValue(UserPost.class);
							queue.remove(post);
						}
					}
					
				}
				
			});
			
		}

	}

}
