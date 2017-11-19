package profile;

import java.awt.Image;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;


public class AccountModel {
	
	private String name;
	private Image picture;
	private List<AccountModel> friends;
	private MessageWallModel wall;
	private NewsFeedModel feed;
	
	public AccountModel(String name, Image picture) {
		this.name = name;
		this.picture = picture;
		friends = new ArrayList<AccountModel>();
		wall = new MessageWallModel();
		feed = new NewsFeedModel();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Image getPicture() {
		return picture;
	}
	
	public void setPicture(Image picture) {
		this.picture = picture;
	}
	
	public List<AccountModel> getFriends() {
		return friends;
	}
	
	public void addFriend(AccountModel account) {
		friends.add(account);
	}
	
	public MessageWallModel getWall() {
		return wall;
	}
	
	public NewsFeedModel getFeed() {
		return feed;
	}
	
	public void post(String message) {
		wall.post(message);
		
		AccountModel friend;
		Iterator<AccountModel> iterator = friends.iterator();
		while (iterator.hasNext()) {
			friend = iterator.next();
			friend.getFeed().post(this, message);
		}
	}
}
