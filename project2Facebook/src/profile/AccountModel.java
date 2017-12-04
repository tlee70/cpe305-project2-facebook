package profile;

import java.awt.Image;

import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;

import java.util.LinkedList;
import java.util.Iterator;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class AccountModel {
	
	private static final String DEFAULT_BLANK_PIC_PATH = "pics/blank.png"; 
	
	private String name;
	private Image picture;
	private String picturePath;
	private List<AccountModel> friends;
	private LinkedList<String> wall;
	private NewsFeedModel feed;
	
	private List<WallObserver> wallObs;
	private List<PictureObserver> picObs;
	private List<NewsFeedObserver> feedObs;
	private List<FriendsListObserver> friendsListObs;
	
	public AccountModel(String name) {
			this(name, DEFAULT_BLANK_PIC_PATH);
	}
	
	public AccountModel(String name, String imagefilePath) {
		this.name = name;
		this.picturePath = imagefilePath;
		
		friends = new LinkedList<AccountModel>();
		wall = new LinkedList<String>();
		feed = new NewsFeedModel();
		wallObs = new LinkedList<WallObserver>();
		picObs = new LinkedList<PictureObserver>();
		feedObs = new LinkedList<NewsFeedObserver>();
		friendsListObs = new LinkedList<FriendsListObserver>();
		
		try {
			Image picture = ImageIO.read(new File("pics/blank.png"));
			this.picture = picture;
		}
		catch (IOException e) {
			System.out.println(e);
			System.out.println(e.getStackTrace());
		}
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * Overrides default toString method for purposes of displaying in GUI components
	 * Returns name of AccountModel
	 * 
	 * @return the name associated with the AccountModel
	 */
	public String toString() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Image getPicture() {
		return picture;
	}
	
	public String getPicturePath() {
		return picturePath;
	}

	public void setPicture(String imageFilePath) {
		try {
			Image picture = ImageIO.read(new File(imageFilePath));
			this.picture = picture;
			this.picturePath = imageFilePath;
		}
		catch (IOException e) {
			System.out.println(e);
			System.out.println(e.getStackTrace());
		}

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
	
	public String getFeedPosts() {
		return feed.toString();
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
		if ( !(acc.getName().equals(name)) ) {
			return false;
		} else if ( !(acc.getPicturePath().equals(picturePath)) ) {
			return false;
		}
		
		return true;
	}
	
	public String toJSON() {
		StringBuilder builder = new StringBuilder();
		String newlineChar = System.getProperty("line.separator");
		
		builder.append("{");
		builder.append(newlineChar);
		builder.append("\t");
		builder.append("\"name\": ");
		builder.append("\"" + name + "\"");
		builder.append(newlineChar);
		builder.append("\t");
		builder.append("\"picturePath\": ");
		builder.append("\"" + picturePath + "\"");
		builder.append(newlineChar);
		builder.append("}");
		
		return builder.toString();
		
	}
	
	/**
	 * Saves the state of the AccountModel for comparison and possible recovery
	 * List of friends, wall info, and news feed stored as separate text files
	 */
	public void saveState() {
		jsonWriteCollection(friends, "files/friends.txt");
		jsonWriteCollection(wall, "files/wall.txt");
		feed.saveState("files/feed.txt");
	}

	public void jsonWriteCollection(Collection<?> collection, String fileName) {
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
