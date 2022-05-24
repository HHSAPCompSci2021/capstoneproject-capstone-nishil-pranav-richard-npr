package databaseData;

/**
 * The IntegerPost class represents an integer. 
 * It is used to help detect when things have been loaded in from Firebase or once something has been uploaded to Firebase.
 * @author Nishil Anand
 * */
public class IntegerPost extends Post {
	
	private int x;
	
	
	/**
	 * Constructs a new IntegerPost
	 */
	public IntegerPost() {
		super("INT");
		x = 0;
	}
	
	
	/**
	 * Sets the value this Post holds to value.
	 * @param value the new value for this Post to hold.
	 */
	public void setValue(int value) {
		this.x = value;
	}
	
	/**
	 * Returns the value that this Post holds.
	 * @return the value that this Post holds
	 */
	public int getValue() {
		return x;
	}
	
	/**
	 * Returns a String representing this class. Useful for debugging.
	 * @return a String representing this class.
	 */
	public String toString() {
		return "IntegerPost: " + Integer.toString(x);
	}
	
}