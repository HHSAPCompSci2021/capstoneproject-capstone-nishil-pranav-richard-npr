package databaseData;

public class UserPost extends Post {
	
	public String playerName;
	public String playerID;
	
	public UserPost() {
		super("USER");
	}
	
	public UserPost(String playerName) {
		super("USER");
		this.playerName = playerName;
		this.playerID = "";
	}
	
	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public String getPlayerID() {
		return playerID;
	}
	
}