package gameElements.board;

import java.util.HashMap;

import core.DrawingSurface;
import gameElements.pieces.GamePiece;
import processing.core.PApplet;
import processing.core.PImage;

public class Card {
	private String cardName;
	private int health, damage, imgIndex, energy;
	
	public Card(String name, int h, int d, int energy, int image) {
		cardName = name;
		health = h; 
		damage = d;
		this.energy = energy;
		imgIndex = image;
	}
	
	
	public void draw(DrawingSurface surface, float x, float y, float width, float height) {
		surface.stroke(0);
		surface.fill(200);
		surface.rect(x, y, width, height);
		surface.fill(0);
		surface.text(cardName, x-25, y-20);
		surface.text(health+"\n"+damage+"\n"+energy, x-25, y);
		PImage i = surface.getImages().get(imgIndex);
		i.resize((int)(width/2), (int)(height/2));
		surface.image(i, x+width/2-20, y+height/6);
	}
	
	public String getPiece() {
		return cardName;
	}
	
	public boolean isPointInside(float cx, float cy, float x, float y, float width, float height) {
		if(cx >= x && cx <=x+width && cy >= y && cy <=y+height) {
			return true;
		}
		return false;
	}
	
	
	
}
