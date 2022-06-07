package gameElements.screens;

/**
 * Has the screen codes for the Screens.
 * 
 * @author Nishil Anand
 * @author John Shelby
 */
public interface ScreenCodes {
	
	/**
	 * Represents the Menu screen
	 */
	public static final int SCREEN1 = 0;
	
	/**
	 * Represents the Second screen
	 */
	public static final int SCREEN2 = 1;

	/**
	 * Represents the OnlineNameCreate screen
	 */
	public static final int SCREEN3 = 2;

	/**
	 * Represents the Queue screen
	 */
	public static final int SCREEN4 = 7;

	/**
	 * Represents the OnlineGame screen
	 */
	public static final int SCREEN8 = 3;

	/**
	 * Represents the LocalGame screen
	 */
	public static final int SCREEN5 = 4;

	/**
	 * Represents the LocalNameCreate screen
	 */
	public static final int SCREEN6 = 5; 

	/**
	 * Represents the Instructions screen
	 */
	public static final int SCREEN7 = 6; 
	
	/**
	 * Switches the screen
	 * @param i the code for the screen to switch to
	 */
	public void switchScreen(int i);
}
