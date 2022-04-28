



import java.awt.Point;
import java.awt.Rectangle;

import processing.core.PApplet;


public class Menu extends Screen {
	
	private DrawingSurface surface;
	
	private Rectangle button;
	
	public Menu(DrawingSurface surface) {
		super(800,600);
		this.surface = surface;
		
		button = new Rectangle(800/2-100,600/2-50,200,100);
	}
	
	
	public void draw() {
		
		surface.pushStyle();
		surface.background(255,255,255);
		
		surface.rect(button.x, button.y, button.width, button.height, 10, 10, 10, 10);
		
		surface.fill(0);
		surface.textAlign(surface.CENTER);
		surface.textSize(20);
		String str = "Play!";
		surface.text(str, button.x+button.width/2, button.y+button.height/2+5);
		
		surface.popStyle();
		
//		System.out.println(button.y + "         " + surface.mouseX + "      " + surface.mouseY);
		
	}
	
	
	public void mousePressed() {
		Point p = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
		if (button.contains(p))
			surface.switchScreen(ScreenSwitcher.SCREEN2);
	}
	

}

