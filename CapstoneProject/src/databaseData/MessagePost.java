package databaseData;

public class MessagePost extends Post {
	
	public String data;
	
	public MessagePost() {
		
	}
	
	public MessagePost(String newData) {
		data = newData;
	}
	
	public String getData() {
		return data;
	}
	
}