package databaseData;

public class TestPost extends Post {
	
	private int x;
	
	public TestPost() {
		super("TESTING");
	}
	
	public TestPost(int x) {
		super("TESTING");
		this.x = x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getX() {
		return this.x;
	}
	
}