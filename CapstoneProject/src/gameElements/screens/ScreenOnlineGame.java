package gameElements.screens;

import java.awt.event.ActionListener;

import com.google.firebase.database.DatabaseReference;

import core.DrawingSurface;

public class ScreenOnlineGame extends Screen {
	
	private DrawingSurface surface;
	private DatabaseReference boardRef;
	
	public ScreenOnlineGame(DrawingSurface surface) {
		super(1200,600);
		this.surface = surface;
	}
	
	public void draw() {
		surface.background(255);
		if (boardRef == null) {
			surface.background(0);
		}
	}
	
	public void setBoardRef(DatabaseReference boardRef) {
		this.boardRef = boardRef;
	}
	
	
}