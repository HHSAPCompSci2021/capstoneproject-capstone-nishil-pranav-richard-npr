package gameElements.screens;



public interface ScreenSwitcher {
	public static final int SCREEN1 = 0;		// Menu
	public static final int SCREEN2 = 1;		// Second
	public static final int SCREEN3 = 2;		// OnlineNameCreate
	public static final int SCREEN4 = 3;		// Queue
	public static final int SCREEN5 = 4;		// Game
	public static final int SCREEN6 = 5;		// LocalNameCreate
	
	public void switchScreen(int i);
}
