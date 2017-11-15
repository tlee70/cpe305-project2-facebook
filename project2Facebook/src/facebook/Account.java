package facebook;

import java.awt.Image;
import java.util.List;

public class Account {
	
	private String name;
	private Image picture;
	private List<Account> friends;
	private MessageWall wall;
	
	private List<BasicInformationObserver> basicInformationObservers; 
	private List<MessageBoardObserver> messageWallObservers;
	
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
	
	public List<Account> getFriends() {
		return friends;
	}
	
	public void addFriend(Account account) {
		friends.add(account);
	}
	
	public MessageWall getWall() {
		return wall;
	}
	
	public List<BasicInformationObserver> getBasicInformationObservers() {
		return basicInformationObservers;
	}
	
	public void addBasicInformationObserver(BasicInformationObserver basicInformationObserver) {
		this.basicInformationObservers.add(basicInformationObserver);
	}
	
	public List<MessageBoardObserver> getMessageWallObservers() {
		return messageWallObservers;
	}
	
	public void addMessageWallObserver(MessageBoardObserver messageWallObserver) {
		this.messageWallObservers.add(messageWallObserver);
	}
	
}
