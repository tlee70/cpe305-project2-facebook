package profile;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.StringBuilder;

public class NewsFeedModel {
	private List<Pair<AccountModel,String> > posts;
	
	public NewsFeedModel() {
		posts = new LinkedList<Pair<AccountModel, String>>();
	}
	
	public void post(AccountModel acc, String message) {
		posts.add(new Pair<AccountModel, String>(acc, message));
	}
	
	public List<Pair<AccountModel, String>> getPosts() {
		return posts;
	}
	
	public String getLatestPost() {
		return posts.get(posts.size()-1).toString();
	}
	
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
	
	public void saveState(String fileName) {
		File file = new File(fileName);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
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
