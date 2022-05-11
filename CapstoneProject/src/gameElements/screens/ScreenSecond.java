package gameElements.screens;

import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import core.DrawingSurface;
import databaseData.Post;
import gameElements.board.Board;
import gameElements.board.Card;
import gameElements.board.Location;
import gameElements.board.Player;
import gameElements.pieces.GamePiece;
import core.ImageCodes;
import processing.core.PImage;


public class ScreenSecond extends Screen {
    
    private int x, y;
    private String message;
    private Board b;
    private DrawingSurface surface;
    private ArrayList<PImage> images;
    private Player p;
    
    
    public ScreenSecond(DrawingSurface surface) {
        super(1200,600);
        this.surface = surface;
        images = surface.getImages();
        x = 600;
        y = 100;
        b = new Board();
     //   p = new Player(b, true);
        message = "";
    }
    
    public void draw() {
        surface.pushStyle();
        surface.background(255);   // Clear the screen with a white background
        surface.stroke(0);     // Set line drawing color to white
        surface.noFill();
        surface.fill(0);
        surface.text("Move: Arrow keys",10,30);
        surface.text("Menu & clear database: `",10,50);
        surface.text("Send to databse: Enter",10,70);
        surface.text(message,10,120);
        surface.popStyle();
        
        b.draw(surface, 5, 5, surface.height-10, surface.height-10);

        // this is where the image drawing magic happens
        Point2D.Float vals = b.getCoordFromIndex(5, 5, surface.height-10, surface.height-10, 5, 6);//TODO hardcoded, can be fixed later
    	images.get(ImageCodes.WHITE_KING).resize(53, 53);
        surface.image(images.get(ImageCodes.WHITE_KING), vals.x+2, vals.y+2); 
        
        
        //Player and Card drawing
        float tempX = surface.height+10, tempY = 10f;
        for(int i = 0; i < p.getCards().size() && i < 5; i++) {
        	Card c = p.getCards().get(i);
        	c.draw(surface, tempX, tempY, 100, 100);
        	tempY+=120;
        }
        
    }
    
//    public void keyPressed() {
//        if (surface.key == '`') {
//            surface.clearAllData();
//            surface.switchScreen(ScreenSwitcher.SCREEN1);
//        } else if (surface.keyCode == KeyEvent.VK_ENTER) {
//            surface.postData(new Post(message));
//            message = "";
//        } else if (surface.keyCode == KeyEvent.VK_BACK_SPACE) {
//            message = message.substring(0, message.length()-1);
//        } else {
//            message += surface.key;
//        }
//    }
}