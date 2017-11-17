package profile;

import java.awt.Image;
import java.util.List;


public class AccountModel {
	
	private String name;
	private Image picture;
	private List<AccountModel> friends;
	private MessageWall wall;
	
	
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
	
	public MessageWall getWall() {
		return wall;
	}
	
}
