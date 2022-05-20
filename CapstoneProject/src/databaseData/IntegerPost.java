package databaseData;

public class IntegerPost extends Post {
	
	private int x;
	
	public IntegerPost() {
		super("INT");
	}
	
	public IntegerPost(int x) {
		super("INT");
		this.x = x;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Returns a String representing this class. Useful for debugging.
	 * @return a String representing this class.
	 */
	public String toString() {
		return "IntegerPost: " + Integer.toString(x);
	}
	
}