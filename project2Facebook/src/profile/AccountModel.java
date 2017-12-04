package profile;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import java.util.LinkedList;
import java.util.Iterator;

import org.json.*;

public class AccountModel {
	
	private String name;
	private Image picture;
	private List<AccountModel> friends;
	private MessageWallModel wall;
	private MessageWallModel feed;
	
	private List<WallObserver> wallObs;
	private List<PictureObserver> picObs;
	private List<NewsFeedObserver> feedObs;
	private List<FriendsListObserver> friendsListObs;
	
	public AccountModel(String name, Image picture) {
		this.name = name;
		this.picture = picture;
		friends = new LinkedList<AccountModel>();
		wall = new MessageWallModel();
		feed = new MessageWallModel();
		wallObs = new LinkedList<WallObserver>();
		picObs = new LinkedList<PictureObserver>();
		feedObs = new LinkedList<NewsFeedObserver>();
		friendsListObs = new LinkedList<FriendsListObserver>();
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

	
	public void setPicture(Image image) {
		if (image != null) {
			picture = image;
				
			PictureObserver observer;
			Iterator<PictureObserver> observersIterator = picObs.iterator();
			while (observersIterator.hasNext()) {
				observer = observersIterator.next();
				observer.updatePic(this.picture);
			}
		}
	}
	
	public MessageWallModel getWall() {
		return wall;
	}
	
	public MessageWallModel getFeed() {
		return feed;
	}
	
	public List<AccountModel> getFriends() {
		return friends;
	}
	
	public void addFriend(AccountModel friend) {
		if ( !friends.contains(friend) ) {
			friends.add(friend);
			
			FriendsListObserver observer;
			Iterator<FriendsListObserver> observersIterator = friendsListObs.iterator();
			while (observersIterator.hasNext()) {
				observer = observersIterator.next();
				observer.notifyFriendAdd(friend);
			}
		}
		
		if ( !friend.getFriends().contains(this) ) {
			friend.addFriend(this);
		}
	}
	
	public void removeFriend(AccountModel friend) {
		if (friends.contains(friend)) {
			friends.remove(friend);
			
			FriendsListObserver observer;
			Iterator<FriendsListObserver> observersIterator = friendsListObs.iterator();
			while (observersIterator.hasNext()) {
				observer = observersIterator.next();
				observer.notifyFriendRemove(friend);
			}
		}
		
		if (friend.getFriends().contains(this)) {
			friend.removeFriend(this);
		}
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
	
	public void addFriendsListObserver(FriendsListObserver observer) {
		friendsListObs.add(observer);
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
		
		// notifies WallObservers (OwnProfileController and FriendProfileController)
		WallObserver observer;
		Iterator<WallObserver> observersIterator = wallObs.iterator();
		while (observersIterator.hasNext()) {
			observer = observersIterator.next();
			observer.updateWall(message);
		}
	}
	
	public void friendPost(AccountModel friend, String message) {
		String text = friend.getName() + ": " + message;
		//String text = message;
		
		feed.post(text);
		
		NewsFeedObserver observer;
		Iterator<NewsFeedObserver> observersIterator = feedObs.iterator();
		while (observersIterator.hasNext()) {
			observer = observersIterator.next();
			observer.updateFeed(text);
		}
	}

	public boolean equals(Object other) {
		if (other == null)
			return false;
		if ( !(other instanceof AccountModel) )
			return false;
		
		AccountModel acc = (AccountModel)other;
		return (acc.getName().equals(name));
	}
	
	/**
	 * Saves the state of the AccountModel for comparison and possible recovery
	 * List of friends, wall info, and news feed stored as separate text files
	 */
	public void saveState() {
		saveFriends();
	}

	@SuppressWarnings("unchecked")
	private void saveFriends() {
		//JSONArray jsonFriends = new JSONArray(friends);
		JSONArray jsonFriends = new JSONArray();
		Iterator<AccountModel> iterator = friends.iterator();
		while(iterator.hasNext()) {
			AccountModel friend = iterator.next();
			jsonFriends.put(friend.getName());
		}
		
		System.out.println(jsonFriends.toString(4));
	}
	
	private void saveWall() {
		
	}
	
	private void saveFeed() {
		
	}
}
