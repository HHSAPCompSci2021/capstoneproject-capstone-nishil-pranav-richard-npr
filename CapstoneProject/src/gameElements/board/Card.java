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
	
	
	public void draw(PApplet drawer, float x, float y, float width, float height, DrawingSurface s) {
		drawer.stroke(0);
		drawer.fill(200);
		drawer.rect(x, y, width, height);
		drawer.fill(0);
		drawer.text(cardName, x-25, y-20);
		drawer.text(health+"\n"+damage+"\n"+energy, x-25, y);
		PImage i = s.getImages().get(imgIndex);
		i.resize((int)(width/2), (int)(height/2));
		drawer.image(i, x+width/2-20, y+height/6);

		
	}
	
	public String getPiece() {
		return cardName;
	}
	
	public boolean isPointInside(float cx, float cy, float x, float y, float width, float height) {
		if(cx < x) {
//			System.out.println("1");
			return false;
		}
		if(cx > x+width) {
//			System.out.println("2");
			return false;
		}
		if(cy < y) {
			return false;
		}
		if(cy > y+height) {
			return false;
		}
//		if(cx >= x && cx <=x+width && cy >= y && cy <=y+height) {
//			return true;
//		}
		System.out.println("SUCESS");
		return true;
	}
	
	
	
}
