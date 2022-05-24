package gameElements.screens;


public interface ScreenSwitcher {
	public static final int SCREEN1 = 0;		// Menu
	public static final int SCREEN2 = 1;		// Second
	public static final int SCREEN3 = 2;		// OnlineNameCreate
	public static final int SCREEN4 = 7;		// Queue
	public static final int SCREEN8 = 3;		// OnlineGame
	public static final int SCREEN5 = 4;		// LocalGame
	public static final int SCREEN6 = 5;		// LocalNameCreate
	public static final int SCREEN7 = 6;		// Instructions
	
	public void switchScreen(int i);
}
