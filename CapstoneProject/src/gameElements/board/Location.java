package gameElements.board;
import gameElements.pieces.*;
public class Location {
	private GamePiece gp;
	
	public Location(GamePiece g) {
		gp = g;
	}
	
	public GamePiece get() {
		return gp;
	}
	

}
