package profile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import login.LoginModel;

/**
 * Class for holding overall collection of LoginModels and paired AccountModels
 * Extension of HashMap for implementation of JSON-related methods specific to this project
 * @author Tim
 *
 */
public class FacebookDatabase extends HashMap<LoginModel, AccountModel> {

	private static final File LOGIN_FILE = new File("files/login_data.txt");
	
	/**
	 * Gets all accounts as a Map of username/AccountModel pairs for easy searching
	 * @return Map of paired username/AccountModel for ease of searching
	 */
	public Map<String, AccountModel> getAccounts() {
		Map<String, AccountModel> accounts = new HashMap<String, AccountModel>();
		
		Iterator<Map.Entry<LoginModel,AccountModel>> iterator = entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<LoginModel, AccountModel> pair = iterator.next();
			AccountModel account = pair.getValue();
			
			accounts.put(account.getName(), account);
		}
		
		return accounts;
	}
	
	/**
	 * Saves all login-account pairs to file specified by LOGIN_FILE
	 * Delegates to entries' toJSON() methods, then ensures proper formatting in overall JSONArray
	 */
	public void saveState() {
		String newLineChar = System.getProperty("line.separator");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(LOGIN_FILE));
			String newlineChar = System.getProperty("line.separator");
			
			writer.write("[");
			writer.flush();
			
			Iterator<Map.Entry<LoginModel,AccountModel>> iterator = entrySet().iterator();
			while(iterator.hasNext()) {
				writer.write(newlineChar + "\t{");
				
				Map.Entry<LoginModel, AccountModel> pair = iterator.next();
				LoginModel login = pair.getKey();
				AccountModel account = pair.getValue();
				
				writer.write(newLineChar + "\t\t\"LoginModel\":");
				String loginStr = login.toJSON().replaceAll("(?m)^", "\t\t");
				writer.write(loginStr.replaceFirst("\t\t", " ") + ",");
				
				writer.write(newLineChar + "\t\t\"AccountModel\":");
				String accountStr = account.toJSON().replaceAll("(?m)^", "\t\t");
				writer.write(accountStr.replaceFirst("\t\t", " "));
				
				writer.write(newLineChar + "\t}");
				
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
	 * Initializes database from JSON-formatted file
	 */
	public void initialize() {
		try {
			FileReader reader = new FileReader(LOGIN_FILE);
			
			JSONParser parser = new JSONParser();
		    Object obj = parser.parse(reader);
			JSONArray jarr = (JSONArray)obj;
			
			Iterator iterator = jarr.iterator();
			while (iterator.hasNext()) {
				JSONObject jobj = (JSONObject)iterator.next();
				
				LoginModel login = new LoginModel( (JSONObject)jobj.get("LoginModel") );
				AccountModel account = new AccountModel( (JSONObject)jobj.get("AccountModel") );
				
				this.put(login, account);
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
