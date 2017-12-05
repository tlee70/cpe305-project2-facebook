package login;

import profile.FacebookDatabase;

/**
 * Basic driver for running Facebook project
 * 
 * @author Tim
 *
 */
public class FacebookDriver {
	public static void main(String[] args) {
		FacebookDatabase database = new FacebookDatabase();
		database.initialize();
		
		new LoginController(new LoginView(), database);
	}
}
