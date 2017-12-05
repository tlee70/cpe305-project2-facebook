package tests;

import org.junit.*;
import static org.junit.Assert.*;

import profile.NewsFeedModel;
import profile.AccountModel;

public class NewsFeedModelTester {
	@Test
	public void postTester() {
		NewsFeedModel feed = new NewsFeedModel();
		
		feed.post(new AccountModel("a", "pics/blank.png"), "message");
		
		assertEquals("a: message", feed.getLatestPost());
	}
}
