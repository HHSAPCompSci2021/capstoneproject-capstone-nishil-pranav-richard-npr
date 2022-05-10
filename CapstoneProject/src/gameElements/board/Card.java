package gameElements.board;

import core.DrawingSurface;
import processing.core.PApplet;
import processing.core.PImage;

public class Card {
	private String cardName;
	private int health, damage, imgIndex;
	
	public Card(String name, int h, int d, int image) {
		cardName = name;
		health = h; 
		damage = d;
		imgIndex = image;
	}
	
	
	public void draw(PApplet drawer, float x, float y, float width, float height, DrawingSurface s) {
		drawer.stroke(0);
		drawer.fill(200);
		drawer.rect(x, y, width, height);
		drawer.fill(0);
		drawer.text(cardName, x-25, y-20);
		drawer.text(health+"\n"+damage, x-25, y);
		
		PImage i = s.getImages().get(imgIndex);
		i.resize((int)(width/2), (int)(height/2));
		drawer.image(i, x+width/2-20, y+height/6);

		
	}
	
	
}
