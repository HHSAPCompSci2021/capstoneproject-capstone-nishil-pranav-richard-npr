package databaseData;

public class UserPost extends Post {
	
	private String playerName;
	private String playerID;
	
	public UserPost() {
		
	}
	
	public UserPost(String playerName) {
		this.playerName = playerName;
		this.playerID = "";
	}
	
	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
}