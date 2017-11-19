package profile;

import java.util.List;
import java.util.LinkedList;

public class NewsFeedModel {
	private List<String> feed;
	
	public NewsFeedModel() {
		feed = new LinkedList<String>();
	}
	
	public void post(AccountModel account, String message) {
		feed.add(account.getName() + ": " + message);
	}
	
	public List<String> getFeed() {
		return feed;
	}
}
