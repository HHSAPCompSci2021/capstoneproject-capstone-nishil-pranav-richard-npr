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

import databaseData.BoardPost;
import databaseData.ChangePost;
import databaseData.Post;
import databaseData.UserPost;
import gameElements.board.Board;
import gameElements.screens.Screen;
import gameElements.screens.ScreenInstructions;
import gameElements.screens.ScreenLocalGame;
import gameElements.screens.ScreenLocalNameCreate;
import gameElements.screens.ScreenMenu;
import gameElements.screens.ScreenOnlineGame;
import gameElements.screens.ScreenOnlineNameCreate;
import gameElements.screens.ScreenQueue;
import gameElements.screens.ScreenSecond;
import gameElements.screens.ScreenSwitcher;
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
	private ArrayList<UserPost> queue;
	private String playerName;
	private Board board;
//	public UserPost player;		// the player running this program, NETWORKING ONLY. may be null		// TODO: make private later maybe
	
	// Database stuff
	private DatabaseReference ref;
	private DatabaseReference gameRef;
	
	private static int i;
	
	/**
	 * Constructs a new DrawingSurface, setting up fields, the database, and screens.
	 */
	public DrawingSurface() {
		
		// SETUP NORMAL FIELDS
		playerName = null;
//		board = null;
		queue = new ArrayList<UserPost>();
		
		
		// DATABASE SETUP
		FileInputStream refreshToken;
		DatabaseReference queueRef = null;
		DatabaseReference gamesRef = null;
		try {

			refreshToken = new FileInputStream("dataBaseKey.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(refreshToken))
					.setDatabaseUrl("https://chessroyale-e5d70-default-rtdb.firebaseio.com/")
					.build();

			FirebaseApp.initializeApp(options);
			ref = FirebaseDatabase.getInstance().getReference();
			queueRef = ref.child("Queue");
			gamesRef = ref.child("Games");
			
			ref.addChildEventListener(new DatabaseChangeListener());
			queueRef.addChildEventListener(new DatabaseChangeListener());
			gamesRef.addChildEventListener(new DatabaseChangeListener());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
//		board = new Board();
		
	}
	
	/**
	 * Sets up the images.
	 */
	public void setup() {
		
		// LOAD IMAGES
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
		
	
	
	}

	/**
	 * Draws the current active screen.
	 */
	public void draw() {
		
//		if (i == 0) return;
		
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
 if (activeScreen == screens.get(ScreenSwitcher.SCREEN3)) {
			((ScreenOnlineNameCreate) activeScreen).keyPressed();
		} else if (activeScreen == screens.get(ScreenSwitcher.SCREEN6)) {
			((ScreenLocalNameCreate) activeScreen).keyPressed();
		} else if (activeScreen == screens.get(ScreenSwitcher.SCREEN5)) {
			((ScreenLocalGame) activeScreen).keyPressed();
		} else if (activeScreen == screens.get(ScreenSwitcher.SCREEN8)) {
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
//		String path = "Folder";
//		DatabaseReference postRef = ref.child(path);
//		DatabaseReference pushedPostRef = postData(new MessagePost("Hello, world!"), postRef);
//		System.out.println(pushedPostRef.getKey());
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
	 * Returns an i value. This is a value that is incremented every time something is added to the queue.
	 * Used for IDing.
	 * 
	 * @return an i value
	 */
	public int getI() {
		return i;
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
		Screen screen = screens.get(7);
		if (this.activeScreen.equals(screens.get(7))) {
			ScreenQueue queueScreen = (ScreenQueue) screen;
			queueScreen.queueUpdated();
		}
	}
	
	/**
	 * Tells the ScreenQueue that a game was made if it is the active screen
	 */
	public void gameCreated(BoardPost post) {
		Screen queueScreen = screens.get(7);
		Screen gameScreen = screens.get(3);
		if (this.activeScreen.equals(screens.get(7))) {
			ScreenQueue queueScreen2 = (ScreenQueue) queueScreen;
			queueScreen2.gameCreated(post);
		} else if (this.activeScreen.equals(screens.get(3))) {
			ScreenOnlineGame gameScreen2 = (ScreenOnlineGame) gameScreen;
//			gameScreen2.setBoardRef(post);
		}
		  
	}
	
	/**
	 * Tells the ScreenOnlineGame that a piece was added if it is the active screen
	 * @param post the post of the piece to add
	 */
	public void pieceAdded(ChangePost post) {
		if (this.activeScreen.equals(screens.get(3))) {
			ScreenOnlineGame screen = (ScreenOnlineGame) screens.get(3);
			screen.pieceAdded(post);
		}
	}
	
	
//	/** 
//	 * Returns an ArrayList of UserPosts with players in the queue.
//	 * @return an ArrayList of UserPosts with players in the queue.
//	 */
//	public ArrayList<UserPost> getQueue() {
//		return queue;
//	}

	/**
	 * 
	 * Handles all changes to the database reference. Because Firebase uses a separate thread than most other processes we're using (both Swing and Processing),
	 * we need to have a strategy for ensuring that code is executed somewhere besides these methods.
	 * 
	 * @author john_shelby, Nishil Anand
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
			
//			System.out.println("SNAPSHOT FROM DRAWING " + dataSnapshot);
			
			tasks.add(new Runnable() {
				@Override
				public void run() {
//					postData(new IntegerPost())
					Post postN = dataSnapshot.getValue(Post.class);
					System.out.println("> add " + postN + postN.postType);
					String postType = postN.postType;
					if (postType != null ) {
						if (postType.matches("USER")) {
							UserPost post = dataSnapshot.getValue(UserPost.class);
//							System.out.println(post);
							queue.add(post);
							updatedQueue();
						} else if (postType.matches("BOARD")) {
							BoardPost post = dataSnapshot.getValue(BoardPost.class);
							post.setReference(dataSnapshot.getRef());
							System.out.println("    BOARD ADDED: " + post);
							setBoard(new Board());
							gameCreated(post);
							i += 1;
						} else if (postType.matches("PIECEADDED")) {
							ChangePost post = dataSnapshot.getValue(ChangePost.class);
							System.out.println("    CHANGE: " + post);
							pieceAdded(post);
//							post.setReference(dataSnapshot.getRef());
//							setBoard(post.getBoard());
//							gameCreated(post);
						}
					} else {
//						System.out.println(postType);
					}
//					currentDrawing.addDotSet(post.dots, new Color(post.r,post.g,post.b));
				}
				
			});
		}

		@Override
		public void onChildChanged(DataSnapshot arg0, String arg1) {
			Post postN = arg0.getValue(Post.class);
			String postType = postN.postType;
			if (postType != null && postType.matches("BOARD")) {
				BoardPost post = arg0.getValue(BoardPost.class);
				post.setReference(arg0.getRef());
				System.out.println("    BOARD UPDATED: " + post);
				setBoard(post.getBoard());
//				gameCreated(post);
			}
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
