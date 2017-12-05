package profile;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.LinkedList;
import java.util.Iterator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.StringBuilder;

/**
 * Class representing the news feed of a facebook profile
 * Holds the posts of other people and keeps track of who authored each
 * @author Tim
 *
 */
public class NewsFeedModel {

	private static final File FEED_FILE = new File("files/feed.txt");
	
	private List<Pair<AccountModel, String>> posts;
	
	public NewsFeedModel() {
		posts = new LinkedList<Pair<AccountModel, String>>();
	}
	
	/**
	 * Adds new news 
	 * @param acc the AccountModel who posted
	 * @param message the text of the post
	 */
	public void post(AccountModel acc, String message) {
		posts.add(new Pair<AccountModel, String>(acc, message));
	}
	
	/**
	 * Returns the latest post, formatted as a single String
	 * @return the latest formatted post
	 */
	public String getLatestPost() {
		return posts.get(posts.size()-1).toString();
	}
	
	/**
	 * Returns entire news feed formatted as single String with newlines between each News
	 * 
	 * @return the entire formatted NewsFeedModel
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Iterator<Pair<AccountModel, String>> iterator = posts.iterator();
		while (iterator.hasNext()) {
			builder.append(iterator.next().toString());
			if (iterator.hasNext()) {
				builder.append("\n");
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * Saves the news feed to the specified file in JSON format
	 * Creates JSONArray comprised of News as JSONObjects
	 */
	public void saveState() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(FEED_FILE));
			String newlineChar = System.getProperty("line.separator");
			
			writer.write("[");
			writer.flush();
			
			Iterator<Pair<AccountModel, String>> iterator = posts.iterator();
			while(iterator.hasNext()) {
				Pair<AccountModel, String> pair = iterator.next();
				String str = pair.toJSON().replaceAll("(?m)^", "\t");
				writer.write(newlineChar + str);
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
	 * Initializes news feed based on JSON-formatted text file
	 * static method because implementation is not tied to a specific instance of NewsFeedModel
	 * Does not directly initialize feed, but calls post() for various AccountModels
	 *  
	 * @param accounts the Map of all username/AccountModel pairs, so usernames referenced in file can be properly linked to an AccountModel
	 */
	public static void initialize(Map<String, AccountModel> accounts) {
		try {
			FileReader reader = new FileReader(FEED_FILE);
			
			JSONParser parser = new JSONParser();
		    Object obj = parser.parse(reader);
			JSONArray jarr = (JSONArray)obj;
			
			Iterator iterator = jarr.iterator();
			while (iterator.hasNext()) {
				JSONObject jobj = (JSONObject)iterator.next();
				
				String post = (String)jobj.get("post");
				
				// links author of post to existing AccountModel instead of creating new instance
				String posterName = (String)jobj.get("poster");
				AccountModel poster = accounts.get(posterName);
				if (poster == null) {
					System.out.println("NewsFeedModel could not find AccountModel '" + posterName + "'");
				}
				else {
					// Calling post() will lead back to specific instances of NewsFeedModel, assuming friendList already initialized
					poster.post(post);
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
	 * Generic inner class for pairing AccountModel poster with String text of post
	 * @author Tim
	 *
	 */
	class Pair<L, R> {
		private L left;
		private R right;
		
		public Pair(L left, R right) {
			this.left = left;
			this.right = right;
		}
		
		public L getLeft() {
			return left;
		}
		
		public R getRight() {
			return right;
		}
		
		public String toString() {
			return left.toString() + ": " + right.toString();
		}
		
		/**
		 * Formats Pair as a JSONObject
		 * Uses toString() of fields
		 * @return String representing Pair as a JSONObject
		 */
		public String toJSON() {
			StringBuilder builder = new StringBuilder();
			String newlineChar = System.getProperty("line.separator");
			
			builder.append("{");
			builder.append(newlineChar);
			builder.append("\t\"left\": ");
			builder.append("\"");
			builder.append(left.toString());
			builder.append("\"");
			builder.append(newlineChar);
			builder.append("\t\"right\": ");
			builder.append("\"");
			builder.append(right.toString());
			builder.append("\"");
			builder.append(newlineChar);
			builder.append("}");
			
			return builder.toString();
		}
	}
}
