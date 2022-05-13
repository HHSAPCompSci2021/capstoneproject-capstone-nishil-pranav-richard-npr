package core;
import java.awt.Dimension;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
/**
 * The Main class is the class that contains the PApplet on which the program is executed.
 * @author John Shelby
 * */
public class Main {
	
	public static void main(String[] args) {
		
		// Firebase uses a special logger to control what gets printed to the command line.
		// Just copy/paste these 2 lines to the beginning of your main()
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
	    root.setLevel(ch.qos.logback.classic.Level.ERROR);  // This only shows us firebase errors. Change "ERROR" to "DEBUG" to see lots of database info.
//	    root.setLevel(ch.qos.logback.classic.Level.DEBUG);  // This only shows us firebase errors. Change "ERROR" to "DEBUG" to see lots of database info.
	    
		DrawingSurface drawing = new DrawingSurface();
		PApplet.runSketch(new String[]{""}, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame)canvas.getFrame();

		window.setSize(1200, 600);
		window.setMinimumSize(new Dimension(1200,600));
		window.setMaximumSize(new Dimension(1200, 600));
		window.setLocation(20, 20);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);

		window.setVisible(true);
		
		
		canvas.requestFocus();
		
	}
	
}
