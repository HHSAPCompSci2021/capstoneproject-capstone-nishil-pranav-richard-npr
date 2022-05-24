package gameElements.screens;




import java.awt.Point;
import java.awt.Rectangle;

import core.DrawingSurface;
import processing.core.PConstants;
import core.ImageCodes;


public class ScreenInstructions extends Screen {
	
	private DrawingSurface surface;
	
	private Rectangle button1;				// go back to menu
	
	public ScreenInstructions(DrawingSurface surface) {
		super(1200,600);
		this.surface = surface;
		
		button1 = new Rectangle(1200-200,30,175,50);
	}
	
	public void draw() {
		
		surface.pushStyle();
		surface.imageMode(PConstants.CENTER);
		surface.textAlign(PConstants.CENTER);
		
		surface.image(surface.getImages().get(ImageCodes.BACKGROUND), 1200/2, 600/2, 1200, 600);
		
		surface.textSize(20);
		showButton(button1, "Back");
		
		surface.tint(255, (float)(255*(3.0/5.0)));
		surface.image(surface.getImages().get(ImageCodes.BLACK_SQUARE), 1200/2, 600/2+100, 1200-(200*2), 600-(150*2));
		
		surface.fill(255);
		
		surface.textSize(50);
		surface.text("INSTRUCTIONS", 1200/2, 225);
		
		surface.textSize(20);
		surface.text("When you join the game, you will see a board. On each side of the board"
				 + "\nthere is a king. Your objective is to take the enemy's king before they"
				 + "\ntake yours! To do this, you have several pieces at your disposal. On "
				 + "\nthe sides of the board, you will see what pieces are available to you."
				 + "\nTo add them to the board, press the corresponding number and click to "
				 + "\nadd it to the board. Note that pieces take up power, which you can see "
				 + "\non the side. The pieces are arranged in a list, which displays total "
				 + "\nhealth, damage capacity, and energy needed to use it. Good luck general!", 1200/2, 600/2);
		
		surface.popStyle();
		
	}
	
	
	public void mousePressed() {
		Point p = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
		
		if (button1.contains(p))
			surface.switchScreen(ScreenSwitcher.SCREEN1);
	}
	
	private void showButton(Rectangle rectangle, String buttonText) {
		surface.fill(255);
		surface.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 4);		// 4 is how round the edges is
		surface.fill(0);
		surface.textAlign(PConstants.CENTER);
		surface.text(buttonText, rectangle.x+rectangle.width/2, rectangle.y+rectangle.height/2+6);
	}
	

}

