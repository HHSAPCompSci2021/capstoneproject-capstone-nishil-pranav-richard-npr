package databaseData;

public class MessagePost extends Post {
	
	public String data;
	
	public MessagePost() {
		super("MESSAGE");
	}
	
	public MessagePost(String newData) {
		super("MESSAGE");
		data = newData;
	}
	
	public String getData() {
		return data;
	}
	
}