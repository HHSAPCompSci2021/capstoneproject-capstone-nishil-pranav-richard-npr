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
	
	
	/**
	 * Creates a new Card object with a specified name, health, damage, and energy
	 * @param name The name of the piece this card represents
	 * @param h The health of the piece this card represents
	 * @param d The damage that can be inflicted by the piece this card represents
	 * @param energy The energy of the piece this card represents
	 * @param image The code of the image of the piece this card represents
	 * */
	public Card(String name, int h, int d, int energy, int image) {
		cardName = name;
		health = h; 
		damage = d;
		this.energy = energy;
		imgIndex = image;
	}
	
	/**
	 * Draws this Card on a PApplet
	 * @param surface The DrawingSurface that this card is to be drawn 
	 * @param x The leftmost point of this card
	 * @param y The topmost point of this card
	 * @param width The width of this card
	 * @param height The height of this card
	 * 
	 * */
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
	
	/**
	 * Returns the name of the piece this card represents
	 * @return the name of the piece this card represents
	 * */
	public String getPiece() {
		return cardName;
	}
	 
	/**
	 * Checks if a point is inside of this card
	 * @param cx The x value of the point
	 * @param cy The y value of the point
	 * @param x The leftmost corner of this card
	 * @param y The topmost point of this card
	 * @param width The width of this card
	 * @param height The height of this card
	 * @return True if the point is contained within this card, false otherwise
	 * */
	public boolean isPointInside(float cx, float cy, float x, float y, float width, float height) {
		x -= width/2;
		y -= height/2;
		if(cx >= x && cx <=x+width && cy >= y && cy <=y+height) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the energy of the piece this card represents
	 * @return the energy of the piece this card represents
	 * */
	public int getEnergy() {
		return energy;
	}
	
	
	
}
