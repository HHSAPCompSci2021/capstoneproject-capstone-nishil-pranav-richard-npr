package gameElements.board;

import processing.core.PApplet;

public class Card {
	private String cardName;
	private int health, damage;
	
	public Card(String name, int h, int d) {
		cardName = name;
		health = h; 
		damage = d;
	}
	
	
	public void draw(PApplet drawer, float x, float y, float width, float height) {
		drawer.stroke(0);
		drawer.fill(200);
		drawer.rect(x, y, width, height);
		drawer.fill(0);
		drawer.text(cardName, x+3, y+height/6);
		drawer.text(health+"\n"+damage, x+3, y+height/2);
		
	}
	
	
}
