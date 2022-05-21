package databaseData;

public class NamePost extends Post {
	
	private String whiteName;
	private String blackName;
	
	public NamePost() {
		super("NAME");
	}
	
	public NamePost(String whiteName, String blackName) {
		super("NAME");
		this.whiteName = whiteName;
		this.blackName = blackName;
	}
	
	public String getWhiteName() {
		return whiteName;
	}
	
	public void setWhiteName(String whiteName) {
		this.whiteName = whiteName;
	}
	
	public String getBlackName() {
		return blackName;
	}
	
	public void setBlackName(String blackName) {
		this.blackName = blackName;
	}
	
}