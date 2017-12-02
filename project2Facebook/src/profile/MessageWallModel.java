package profile;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.lang.StringBuilder;

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
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Iterator<String> iterator = posts.iterator();
		while (iterator.hasNext()) {
			builder.append(iterator.next());
			builder.append("\n");
		}
		
		return builder.toString();
	}
}
