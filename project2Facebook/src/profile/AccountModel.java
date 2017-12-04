package profile;

import java.awt.Image;

import java.util.Collection;
import java.util.List;

import profile.NewsFeedModel.Pair;

import java.util.LinkedList;
import java.util.Iterator;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class AccountModel {
	
	private String name;
	private Image picture;
	private List<AccountModel> friends;
	private LinkedList<String> wall;
	private NewsFeedModel feed;
	
	private List<WallObserver> wallObs;
	private List<PictureObserver> picObs;
	private List<NewsFeedObserver> feedObs;
	private List<FriendsListObserver> friendsListObs;
	
	public AccountModel(String name, Image picture) {
		this.name = name;
		this.picture = picture;
		friends = new LinkedList<AccountModel>();
		wall = new LinkedList<String>();
		feed = new NewsFeedModel();
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
		picture = image;

		PictureObserver observer;
		Iterator<PictureObserver> observersIterator = picObs.iterator();
		while (observersIterator.hasNext()) {
			observer = observersIterator.next();
			observer.updatePic(this.picture);
		}
	}
	
	public String getWallPosts() {
		StringBuilder builder = new StringBuilder();
		Iterator<String> iterator = wall.iterator();
		while (iterator.hasNext()) {
			builder.append(iterator.next());
			if (iterator.hasNext()) {
				builder.append("\n");
			}
		}
		
		return builder.toString();
	}
	
	public NewsFeedModel getFeed() {
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
		wall.add(message);
		
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
		feed.post(friend, message);
		
		NewsFeedObserver observer;
		Iterator<NewsFeedObserver> observersIterator = feedObs.iterator();
		while (observersIterator.hasNext()) {
			observer = observersIterator.next();
			observer.updateFeed( feed.getLatestPost() );
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
		jsonWrite(friends, "files/friends.txt");
		jsonWrite(wall, "files/wall.txt");
		feed.saveState("files/feed.txt");
	}

	public void jsonWrite(Collection<?> collection, String fileName) {
		File file = new File(fileName);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			String newlineChar = System.getProperty("line.separator");
			
			writer.write("[");
			writer.flush();
			
			Iterator<?> iterator = collection.iterator();
			while(iterator.hasNext()) {
				Object obj = iterator.next();
				writer.write(newlineChar + "\t\"" + obj.toString() + "\"");
				if (iterator.hasNext())  {
					writer.write(",");
				}
				writer.flush();
			}
			
			writer.write(newlineChar + "]");
			writer.flush();
			
			writer.close();
		}
		catch (IOException e) {
			System.out.println(e.getStackTrace());
			System.out.println(e);
		}
		
	}

}
