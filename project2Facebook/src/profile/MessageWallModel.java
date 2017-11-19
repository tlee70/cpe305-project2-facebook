package profile;

import java.util.List;
import java.util.LinkedList;

public class MessageWallModel {
	private List<String> posts;
	
	public MessageWallModel() {
		posts = new LinkedList<String>();
	}
	
	public void post(String message) {
		posts.add(message);
	}
	
	public List<String> getPosts() {
		return posts;
	}
}
