package profile;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import login.LoginModel;

import java.util.LinkedList;
import java.util.Iterator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.StringBuilder;

public class NewsFeedModel {

	private static final String FEED_FILE = "files/feed.txt";
	
	private List<Pair > posts;
	
	public NewsFeedModel() {
		posts = new LinkedList<Pair>();
	}
	
	public void post(AccountModel acc, String message) {
		posts.add(new Pair(acc, message));
	}
	
	public String getLatestPost() {
		return posts.get(posts.size()-1).toString();
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Iterator<Pair> iterator = posts.iterator();
		while (iterator.hasNext()) {
			builder.append(iterator.next().toString());
			if (iterator.hasNext()) {
				builder.append("\n");
			}
		}
		
		return builder.toString();
	}
	
	public void saveState() {
		File file = new File(FEED_FILE);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			String newlineChar = System.getProperty("line.separator");
			
			writer.write("[");
			writer.flush();
			
			Iterator<Pair> iterator = posts.iterator();
			while(iterator.hasNext()) {
				Pair pair = iterator.next();
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
	
	public static void initialize(Map<String, AccountModel> accounts) {
		try {
			File file = new File(FEED_FILE);
			FileReader reader = new FileReader(file);
			
			JSONParser parser = new JSONParser();
		    Object obj = parser.parse(reader);
			JSONArray jarr = (JSONArray)obj;
			
			Iterator iterator = jarr.iterator();
			while (iterator.hasNext()) {
				JSONObject jobj = (JSONObject)iterator.next();
				
				String post = (String)jobj.get("post");
					
				String posterName = (String)jobj.get("poster");
				AccountModel poster = accounts.get(posterName);
				if (poster == null) {
					System.out.println("NewsFeedModel could not find AccountModel '" + posterName + "'");
				}
				else {
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
	
	class Pair {
		private AccountModel poster;
		private String post;
		
		public Pair(AccountModel left, String right) {
			this.poster = left;
			this.post = right;
		}
		
		public AccountModel getPoster() {
			return poster;
		}
		
		public String getRight() {
			return post;
		}
		
		public String toString() {
			return poster.toString() + ": " + post.toString();
		}
		
		public String toJSON() {
			StringBuilder builder = new StringBuilder();
			String newlineChar = System.getProperty("line.separator");
			
			builder.append("{");
			builder.append(newlineChar);
			builder.append("\t\"poster\": ");
			builder.append("\"");
			builder.append(poster.toString());
			builder.append("\"");
			builder.append(newlineChar);
			builder.append("\t\"post\": ");
			builder.append("\"");
			builder.append(post.toString());
			builder.append("\"");
			builder.append(newlineChar);
			builder.append("}");
			
			return builder.toString();
		}
	}
}
