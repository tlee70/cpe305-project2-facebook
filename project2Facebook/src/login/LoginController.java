package login;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import profile.OwnProfileView;
import profile.OwnProfileController;
import profile.FacebookDatabase;

/**
 * Controller for MVC that displays login screen
 * Checks entered username and password against database of LoginModels
 * Opens an OwnProfileController and OwnProfileView of AccountModel that pairs to LoginModel
 * @author Tim
 *
 */
public class LoginController {
	private LoginView li_view;
	private FacebookDatabase database;
	
	/**
	 * Constructor connects to a view and map of LoginModel/AccountModel pairs
	 * 
	 * @param view the LoginView to control
	 * @param database	the map of LoginModel/AccountModel pairs to check against
	 */
	public LoginController(LoginView view, FacebookDatabase database) {
		this.li_view = view;
		this.database = database;
		
		view.addEnterListener(new LoginListener());
		view.setVisible(true);
	}
	
	/**
	 * Verifies login information and opens corresponding profile when enter button pressed
	 * 
	 * @author Tim
	 *
	 */
	class LoginListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			LoginModel attemptedLogin = new LoginModel(li_view.getUsername(), li_view.getPassword());
			
			if (database.containsKey(attemptedLogin)) {
				li_view.dispose();
				
				// Replaces key in database with key being passed to controller
				// keys are "equal", but need same pointer for modification by settings
				database.put(attemptedLogin, database.remove(attemptedLogin));
				
				new OwnProfileController(new OwnProfileView(), database.get(attemptedLogin),
						attemptedLogin, database);
			}
			else {
				li_view.showError("Incorrect login information");
			}
		}
	}
}
