package gameElements.screens;

import java.awt.Rectangle;
import java.util.ArrayList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import core.DrawingSurface;
import databaseData.BoardPost;
import databaseData.IntegerPost;
import databaseData.UserPost;
import processing.core.PConstants;


/**
 * This Screen is used as a waiting screen while the user is waiting for another player to play the game.
 * It also sends both players to a game (so note that both the 1st and 2nd player go through this screen, though the second player might not notice it).
 * 
 * @author Nishil Anand
 */
public class ScreenQueue extends Screen {
	
	private DrawingSurface surface;
	private DatabaseReference ref;
	private ArrayList<UserPost> queue;
	private ScreenOnlineGame gameScreen;
	
	private Rectangle button;
	
	private boolean firstLoop;
	private DatabaseReference roomCreated;
	
	
	/**
	 * Constructs a new ScreenQueue
	 * 
	 * @param surface the DrawingSurface to draw with
	 * @param ref the DatabaseReference to the main DatabaseFolder, used for creating/accessing a game room and the queue
	 * @param queue the ArrayList of UserPosts containing UserPosts in the queue
	 * @param gameScreen the ScreenOnlineGame that this class helps set up (by setting names and if this player is black or white)
	 */
	public ScreenQueue(DrawingSurface surface, DatabaseReference ref, ArrayList<UserPost> queue, ScreenOnlineGame gameScreen) {
		super(1200,600);
		this.surface = surface;
		this.ref = ref;
		this.queue = queue;
		this.gameScreen = gameScreen;
		this.firstLoop = true;
		
		button = new Rectangle(1200/2-100,600/2-50,200,50);
	}
	
	
	/**
	 * Draws the elements needed to enter a name onto the Processing window.
	 */
	public void draw() {
		
		// CHECK FOR OPPONENTS
		String str = "";
		str = Integer.toString(queue.size());
		
		// make a new game
		if (firstLoop) {
			firstLoop = false;
			if (queue.size() > 0) {		// if there is someone already waiting in queue
				BoardPost board = new BoardPost();
				surface.postData(board);
				
				DatabaseReference gameRef = ref.child(Integer.toString(surface.getI()));
				surface.postData(new IntegerPost(), gameRef);
				
				surface.addChildEventListener(gameRef);
				surface.setGameReference(gameRef);
				gameScreen.setWhite(true);
				gameScreen.setNames(surface.getPlayerName(), surface.getQueue().get(0).getPlayerName());
				surface.setInQueue(null);
				surface.switchScreen(ScreenCodes.SCREEN8);
				return;
			}
		}
		
		// connect to an existing game
		if (roomCreated != null && queue.size() > 0 && queue.size() % 2 == 0) {
			// folder for game replicating	
			DatabaseReference gameRef = roomCreated;
			surface.addChildEventListener(gameRef);
			surface.setGameReference(gameRef);
			gameScreen.setWhite(false);
			
			gameScreen.setNames(surface.getQueue().get(1).getPlayerName(), surface.getQueue().get(0).getPlayerName());
			
			// remove from queue
			surface.clearData(ref.child("Queue"));
			surface.setInQueue(null);
			surface.switchScreen(ScreenCodes.SCREEN8);
			return;
		}
		
		
		// SCREEN STUFF
		surface.pushStyle();
		surface.textSize(20);
		
		surface.background(255,255,255);
		
		showButton(button, str);
		
		surface.popStyle();
		
	}
	
	/**
	 * Called when a room is created.
	 * @param post the DataSnapshot of the room
	 */
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
