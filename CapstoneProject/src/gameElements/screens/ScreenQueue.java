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
import databaseData.MessagePost;
import databaseData.Post;
import databaseData.UserPost;
import processing.core.PApplet;
import processing.core.PConstants;

public class ScreenQueue extends Screen {
	
	private DrawingSurface surface;
	private DatabaseReference ref;
	
	private Rectangle button;
	
	private int i;
	
	public ScreenQueue(DrawingSurface surface, DatabaseReference ref) {
		super(1200,600);
		this.surface = surface;
		this.ref = ref;
		
		button = new Rectangle(1200/2-100,600/2-50,200,50);
		
	}
	
	
	public void draw() {
		
		// SCREEN STUFF
		surface.pushStyle();
		surface.background(255,255,255);
		
		surface.textSize(20);
		
		showButton(button, "Waiting for Opponent");
		
		surface.popStyle();
		
		
		// CHECK FOR OPPONENTS
//		i++;
//		if (i > 60) {
//			i = 0;
//			surface.postData(new MessagePost("Hello world"), ref.child("Test stuff"));
//		}
		
	}
	
	private void showButton(Rectangle rectangle, String buttonText) {
		surface.fill(255);
		surface.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 4);		// 4 is how round the edges is
		surface.fill(0);
		surface.textAlign(PConstants.CENTER);
		surface.text(buttonText, rectangle.x+rectangle.width/2, rectangle.y+rectangle.height/2+6);
	}
	
}

