package gameElements.screens;



public interface ScreenSwitcher {
	public static final int SCREEN1 = 0;		// ScreenMenu
	public static final int SCREEN2 = 1;		// ScreenSecond
	public static final int SCREEN3 = 2;		// ScreenNameCreate
	public static final int SCREEN4 = 3;		// ScreenQueue
	public static final int SCREEN5 = 4;		// ScreenGame
	
	public void switchScreen(int i);
}
