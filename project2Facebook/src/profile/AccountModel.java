package profile;

import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Map;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Model class for MVC holding the state of a Facebook account and various methods for its manipulation
 * 
 * @author Tim
 *
 */
public class AccountModel {

	/**
	 * The file to save/retrieve the friends list to/from
	 */
	private static final File FRIENDS_FILE =  new File("files/friends.txt");
	/**
	 * The file to save/retrieve the wall posts to/from
	 */
	private static final File WALL_FILE = new File("files/wall.txt");
	
	// The name associated with the account and displayed in the profile
	private String name;
	/**
	 * The path to the picture displayed in the profile
	 * 
	 * Stored as String instead of Image of File so it can be saved to text file
	 */
	private String picturePath;
	/**
	 * The List of friends of this AccountModel
	 * 
	 * Friends see each others' wall posts in their news feed
	 * Selecting a friend will bring up a FriendProfileView instead of a StrangerProfileView
	 * All friendships are two-way
	 */
	private List<AccountModel> friends;
	/**
	 * Set of Strings representing an account's wall, where the user can post
	 */
	private LinkedList<String> wall;
	/**
	 * The account's feed displays posts from friends
	 * 
	 * Does not retroactively add/remove posts when someone is followed/unfollowed
	 */
	private NewsFeedModel feed;
	
	private List<WallObserver> wallObs;
	private List<PictureObserver> picObs;
	private List<NewsFeedObserver> feedObs;
	private List<FriendsListObserver> friendsListObs;
	
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

	}
	
	public AccountModel(JSONObject jaccount) {
		this((String)jaccount.get("name"), (String)jaccount.get("picturePath"));
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
	
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if ( !(other instanceof AccountModel) ) {
			return false;
		}
		
		AccountModel otherAcc = (AccountModel)other;
		
		return otherAcc.getName().equals(name); 
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPicturePath() {
		return picturePath;
	}

	/**
	 * Sets the picture (path) of the AccountModel and notifies PictureObservers
	 * 
	 * @param imageFilePath the path to the new image file
	 */
	public void setPicture(String imageFilePath) {
		this.picturePath = imageFilePath;

		PictureObserver observer;
		Iterator<PictureObserver> observersIterator = picObs.iterator();
		while (observersIterator.hasNext()) {
			observer = observersIterator.next();
			observer.updatePic(this.picturePath);
		}
	}
	
	/**
	 * Gets all wall posts formatted with each post on a new line
	 * 
	 * Used in initializing wall of views
	 * 
	 * @return String of all wall posts with newlines separating each post
	 */
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
	
	/**
	 * Gets all feed posts formatted into a single String to initialization of view
	 * 
	 * Formatting delegated to NewsFeedModel class
	 * 
	 * @return formatted String of all news feed posts
	 */
	public String getFeedPosts() {
		return feed.toString();
	}
	
	public List<AccountModel> getFriends() {
		return friends;
	}
	
	/**
	 * Links an different AccountModel as a friend to this one
	 * 
	 * All friendships are two-way
	 * Checks to ensure that new AccountModel is not this and is not already a friend
	 * Notifies FriendsListObservers to update if friend successfully added
	 * 
	 * @param friend the AccountModel to attempt to add
	 */
	public void addFriend(AccountModel friend) {
		if ( !friend.equals(this) && !friends.contains(friend) ) {
			friends.add(friend);
			
			FriendsListObserver observer;
			Iterator<FriendsListObserver> observersIterator = friendsListObs.iterator();
			while (observersIterator.hasNext()) {
				observer = observersIterator.next();
				observer.notifyFriendAdd(friend);
			}
			
			// Prevents infinite recursive call between two AccountModels
			if ( !friend.getFriends().contains(this) ) {
				friend.addFriend(this);
			}
		}
	}
	
	/**
	 * Removes different AccountModel as a friend to this one
	 * 
	 * All friendships are two-way; breaks both sides of link
	 * Notifies FriendsListObservers to update if friend successfully removed
	 * 
	 * @param friend the AccountModel to attempt to add
	 */
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
		
		// Prevents infinite recursion
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
	
	/**
	 * Posts a message on one's own wall and friends' news feeds
	 * 
	 * @param message String making up a facebook wall post
	 */
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
	
	/**
	 * Takes a post from a friend and puts it in own news feed
	 * 
	 * @param friend the friend who posted
	 * @param message the post's contents
	 */
	public void friendPost(AccountModel friend, String message) {
		feed.post(friend, message);
		
		NewsFeedObserver observer;
		Iterator<NewsFeedObserver> observersIterator = feedObs.iterator();
		while (observersIterator.hasNext()) {
			observer = observersIterator.next();
			observer.updateFeed( feed.getLatestPost() );
		}
	}
	
	/**
	 * Deactivation of account: removes all friends and empties wall and friend files
	 * 
	 * feed is NOT emptied because implementation has feed saving posts of other AccountModels
	 */
	public void deactivate() {
		while (friends.size() > 0) {
			removeFriend(friends.get(0));
		}
		
		jsonWriteEmpty(WALL_FILE);
		jsonWriteEmpty(FRIENDS_FILE);
		
		feed.saveState();
	}
	
	/**
	 * Rewrites a file to be an empty JSONArray
	 * 
	 * @param file the file to empty
	 */
	public void jsonWriteEmpty(File file) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write("[]");
			writer.flush();
			
			writer.close();
		}
		catch (IOException e) {
			System.out.println(e.getStackTrace());
			System.out.println(e);
		}
	}
	
	/**
	 * Converts this Object to a JSON-formatted String
	 * 
	 * Used in place of library's JSONWriter for formatting easier to read by humans
	 * 
	 * @return String representing this data in JSON format
	 */
	public String toJSON() {
		StringBuilder builder = new StringBuilder();
		String newlineChar = System.getProperty("line.separator");
		
		builder.append("{");
		builder.append(newlineChar);
		builder.append("\t");
		builder.append("\"name\": ");
		builder.append("\"" + name + "\",");
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
	 * 
	 * List of friends, wall info, and news feed stored as separate text files
	 */
	public void saveState() {
		jsonWriteCollection(friends, FRIENDS_FILE);
		jsonWriteCollection(wall, WALL_FILE);
		feed.saveState();
	}

	/**
	 * Generic helper method for saveState() saves a Collection as a JSONArray to a specified file
	 *
	 * Contents of Collection saved by their toString() method as a string literal
	 * Assumes content of Collection can be represented purely by toString()
	 * 
	 * @param collection the collection to save
	 * @param file the file to write to 
	 */
	public void jsonWriteCollection(Collection<?> collection, File file) {
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
	
	/**
	 * Initializes chosen account from input files w/ JSON format
	 * 
	 * @param accounts List of all accounts; for linking to friends and to feed posts
	 */
	public void initialize(Map<String, AccountModel> accounts) {
		initializeFriends(accounts);
		initializeWall();
		NewsFeedModel.initialize(accounts);
	}
	
	/**
	 * Initializes friends list according to JSON-formatted file (defined as FRIENDS_FILE)
	 * 
	 * Uses Map<String, AccountModel> to link to existing accounts instead of creating new instances
	 * 
	 * @param accounts the map of AccountModels to link to and their name keys
	 */
	private void initializeFriends(Map<String, AccountModel> accounts) {
		try {
			FileReader reader = new FileReader(FRIENDS_FILE);
			
			JSONParser parser = new JSONParser();
		    Object obj = parser.parse(reader);
			JSONArray jarr = (JSONArray)obj;
			
			Iterator iterator = jarr.iterator();
			while (iterator.hasNext()) {
				String name = (String)iterator.next();
				AccountModel friend = accounts.get(name);
				if (friend == null) {
					System.out.println("NewsFeedModel could not find AccountModel '" + name + "'");
				}
				else {
					this.addFriend(friend);
				}

			}			
			reader.close();
		}
		catch(IOException e) {
			System.out.println("Initialize caught IOException: " + e);
			System.out.println(e.getStackTrace());
		}
		catch(ParseException e) {
			System.out.println("Initialize caught ParseException: " + e);
			System.out.println(e.getStackTrace());
		}	
	}
	
	/**
	 * Initializes wall based on JSON-formatted file of posts
	 */
	private void initializeWall() {
		try {
			FileReader reader = new FileReader(WALL_FILE);
			
			JSONParser parser = new JSONParser();
		    Object obj = parser.parse(reader);
			JSONArray jarr = (JSONArray)obj;
			
			Iterator iterator = jarr.iterator();
			while (iterator.hasNext()) {
				String post = (String)iterator.next();
				
				this.post(post);
			}
			
			reader.close();

		}
		catch(IOException e) {
			System.out.println("Initialize caught IOException: " + e);
			System.out.println(e.getStackTrace());
		}
		catch(ParseException e) {
			System.out.println("Initialize caught ParseException: " + e);
			System.out.println(e.getStackTrace());
		}	
	}

}
