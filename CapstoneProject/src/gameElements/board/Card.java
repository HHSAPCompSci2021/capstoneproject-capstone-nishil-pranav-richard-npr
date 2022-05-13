package gameElements.board;

import core.DrawingSurface;
import processing.core.PConstants;
import processing.core.PImage;

/**
 * The Card represents a container which holds an Image of a GamePiece, along 
 * with energy, health, and damage information. 
 * @author Pranav Gunhal
 * */
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
		surface.textAlign(PConstants.CENTER);
		surface.text(cardName, x, y-20);
		surface.text(health+" "+damage+"\n"+energy, x-15, y);
		PImage i = surface.getImages().get(imgIndex);
		surface.image(i, x+width/2-21, y+height/6+5, (int)(width/2), (int)(height/2-6));
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
	
	public int getEnergy() {
		return energy;
	}
	
	
	
}
