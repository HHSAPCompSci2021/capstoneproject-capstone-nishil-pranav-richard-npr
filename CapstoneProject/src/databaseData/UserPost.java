package databaseData;

/**
 * The UserPost class represents a Post of a Player
 * @author Nishil Anand
 * */
public class UserPost extends Post {
	
	private String playerName;
	private String playerID;
	
	/**
	 * Creates a new UserPost object
	 * */
	public UserPost() {
		super("USER");
	}
	
	/**
	 * Constructs a new UserPost object holding a player's name
	 * @param playerName The name of the player this UserPost represents
	 * */
	public UserPost(String playerName) {
		super("USER");
		this.playerName = playerName;
		this.playerID = "";
	}
	
	/**
	 * Sets the name of the player this UserPost represents
	 * @param playerName the name of the player this UserPost represents
	 * */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	/**
	 * Sets the ID of the player this UserPost represents
	 * @param playerID the ID of the player this UserPost represents
	 * */
	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}
	
	/**
	 * Returns the name of the player this UserPost represents
	 * @return the name of the player this UserPost represents
	 * */
	public String getPlayerName() {
		return playerName;
	}
	
	/**
	 * Returns the ID of the player this UserPost represents
	 * @return the ID of the player this UserPost represents
	 * */
	public String getPlayerID() {
		return playerID;
	}
	
}