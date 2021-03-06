package databaseData;

/**
	
	The class you store in the database must fit 3 simple constraints:
	
	The class must have a default constructor that takes no arguments.
	The class must define public getters for the properties to be assigned. Properties without a public getter will be set to their default value when an instance is deserialized.
	The class can only have fields that are:
		- primitive data types
		- ArrayLists (not arrays)
		- Objects of classes that follow these same rules.
	
	Classes from the Java library will often not fit these requirements, so you may need to make simpler classes
	yourself.
	
	I recommend that you *only* use this class for database posts. Don't use this class for storing
	real data in your program. Just create these objects at the moment you want to put something in the
	database, and when you read from the database, quickly turn these objects into some other form that
	is more useful.
	
	One thing that is interesting is that the Firebase database cannot store arrays.
	So, if you want to use a library class that uses arrays (the Color class is one such example), then
	you need to store the data a different, simpler way yourself.
	Note that ArrayLists *can* be stored.
	
	@author john_shelby
	@author Nishil Anand
	
 */
public class Post {
	
	public String postType;
	
	/**
	 * Creates a new Post object, holding nothing
	 * */
	public Post() {
		postType = null;
	}
	
	/**
	 * Creates a new Post object
	 * @param type The type of message this Post holds - MESSAGE or USER
	 * */
	public Post(String type) {
		if (type == "MESSAGE" || type == "USER" || type == "BOARD" || type == "PIECEADDED" || type == "INT") {
			postType = type;
		} else {
			System.err.println("unknown post type: " + type);
			for (StackTraceElement v: Thread.currentThread().getStackTrace()) {
			    System.err.println("    " + v);
			}
		}
	}
	
}