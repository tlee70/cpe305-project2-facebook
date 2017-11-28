package profile;

import java.awt.Image;
import java.util.List;

import java.util.LinkedList;
import java.util.Iterator;


public class AccountModel {
	
	private String name;
	private Image picture;
	private List<AccountModel> friends;
	private MessageWallModel wall;
	private MessageWallModel feed;
	
	private List<WallObserver> wallObs;
	private List<PictureObserver> picObs;
	private List<NewsFeedObserver> feedObs;
	
	public AccountModel(String name, Image picture) {
		this.name = name;
		this.picture = picture;
		friends = new LinkedList<AccountModel>();
		wall = new MessageWallModel();
		feed = new MessageWallModel();
		wallObs = new LinkedList<WallObserver>();
		picObs = new LinkedList<PictureObserver>();
		feedObs = new LinkedList<NewsFeedObserver>();
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Image getPicture() {
		return picture;
	}
	
	public void setPicture(Image picture) {
		if (picture != null)
			this.picture = picture;
		
		
		PictureObserver observer;
		Iterator<PictureObserver> observersIterator = picObs.iterator();
		while (observersIterator.hasNext()) {
			observer = observersIterator.next();
			observer.updatePic(picture);
		}
	}
	
	public List<AccountModel> getFriends() {
		return friends;
	}
	
	public void addFriend(AccountModel account) {
		friends.add(account);
	}
	
	public void addWallObserver(WallObserver observer) {
		wallObs.add(observer);
	}
	
	public void addPicObserver(PictureObserver observer) {
		picObs.add(observer);
	}
	
	public void addFeedObserver(NewsFeedObserver observer) {
		feedObs.add(observer);
	}
	
	public void post(String message) {
		wall.post(message);
		
		// updates friends' feeds
		AccountModel friend;
		Iterator<AccountModel> friendsIterator = friends.iterator();
		while (friendsIterator.hasNext()) {
			friend = friendsIterator.next();
			friend.friendPost(this, message);
		}
		
		// notifies AccountObservers (OwnProfileController and FriendProfileController)
		WallObserver observer;
		Iterator<WallObserver> observersIterator = wallObs.iterator();
		while (observersIterator.hasNext()) {
			observer = observersIterator.next();
			observer.updateWall(message);
		}
	}
	
	public void friendPost(AccountModel friend, String message) {
		String text = friend.getName() + ": " + message;
		
		feed.post(text);
		
		NewsFeedObserver observer;
		Iterator<NewsFeedObserver> observersIterator = feedObs.iterator();
		while (observersIterator.hasNext()) {
			observer = observersIterator.next();
			observer.updateFeed(text);
		}
	}
	
	/**
	 * Saves the state of the AccountModel for comparison and possible recovery
	 * List of friends, wall info, and news feed stored as separate text files
	 */
	public void saveState() {
		
	}
}