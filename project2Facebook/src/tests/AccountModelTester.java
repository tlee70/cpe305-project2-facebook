package tests;

import org.junit.*;
import static org.junit.Assert.*;

import profile.AccountModel;

public class AccountModelTester {
	@Test
	public void toJSONTester() {
		String expected = "{\r\n\t\"name\": \"Name\",\r\n\t\"picturePath\": \"pics/blank.png\"\r\n}";
				
		AccountModel account = new AccountModel("Name", "pics/blank.png");
		
		assertEquals(expected, account.toJSON());
	}
	
	@Test
	public void addFriendTester() {
		AccountModel acc1 = new AccountModel("1", "pics/blank.png");
		AccountModel acc2 = new AccountModel("2", "pics/blank.png");
		AccountModel acc3 = new AccountModel("3", "pics/blank.png");
		
		assertEquals(0, acc1.getFriends().size() );
		assertEquals(0, acc2.getFriends().size() );
		assertEquals(0, acc3.getFriends().size() );
		
		acc1.addFriend(acc2);
		assertEquals(1, acc1.getFriends().size() );
		assertEquals(1, acc2.getFriends().size() );
		assertEquals(0, acc3.getFriends().size() );
		
		assertEquals(acc2, acc1.getFriends().get(0));
		assertEquals(acc1, acc2.getFriends().get(0));
	}
	
	@Test 
	public void removeFriendTester() {
		AccountModel acc1 = new AccountModel("1", "pics/blank.png");
		AccountModel acc2 = new AccountModel("2", "pics/blank.png");
		AccountModel acc3 = new AccountModel("3", "pics/blank.png");
		
		assertEquals(0, acc1.getFriends().size() );
		assertEquals(0, acc2.getFriends().size() );
		assertEquals(0, acc3.getFriends().size() );
		
		acc1.addFriend(acc2);
		assertEquals(1, acc1.getFriends().size() );
		assertEquals(1, acc2.getFriends().size() );
		assertEquals(0, acc3.getFriends().size() );
		
		assertEquals(acc2, acc1.getFriends().get(0));
		assertEquals(acc1, acc2.getFriends().get(0));
		
		acc1.removeFriend(acc3);
		assertEquals(1, acc1.getFriends().size() );
		assertEquals(1, acc2.getFriends().size() );
		assertEquals(0, acc3.getFriends().size() );
		
		assertEquals(acc2, acc1.getFriends().get(0));
		assertEquals(acc1, acc2.getFriends().get(0));
		
		acc1.removeFriend(acc2);
		assertEquals(0, acc1.getFriends().size() );
		assertEquals(0, acc2.getFriends().size() );
		assertEquals(0, acc3.getFriends().size() );
	}
	
	@Test 
	public void friendPostTester() {
		AccountModel acc1 = new AccountModel("1", "pics/blank.png");
		AccountModel acc2 = new AccountModel("2", "pics/blank.png");
		
		acc1.friendPost(acc2, "message");
		assertEquals("2: message", acc1.getFeedPosts());
		
		acc1.friendPost(acc2, "post");
		assertEquals("2: message\n2: post", acc1.getFeedPosts());
	}
	
	@Test 
	public void postTester() {
		AccountModel acc1 = new AccountModel("1", "pics/blank.png");
		AccountModel acc2 = new AccountModel("2", "pics/blank.png");
		
		acc1.addFriend(acc2);
		
		acc1.post("message");
		
		assertEquals("message", acc1.getWallPosts());
		assertEquals("1: message", acc2.getFeedPosts());
	}
}
