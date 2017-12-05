package profile;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import login.LoginModel;

/**
 * BasicProfileController acts as Controller for Facebook MVC
 * Pairs with BasicProfileView to display only name, picture, search bar, settings, and logout
 * Meant to be extended for further functionality
 * 
 * @author Tim
 *
 * @param <T> generic used to allow child classes to use methods from T's child classes
 */
public class BasicProfileController<T extends BasicProfileView> 
	implements PictureObserver {
	
	// View of MVC for displaying facebook profile
	protected T view;
	// AccountModel designated as owner of the view (settings affect this AccountModel)
	protected AccountModel myAcc_model;
	// AccountModel to display information for (can be the same as myAcc_model)
	protected AccountModel dispAcc_model;
	/**
	 * The login information that is associated with myAcc_model
	 * Necessary to modify login information with settings
	 * Cannot be accessed with only myAcc_model and database because database not bidirectional
	 */
	protected LoginModel login_model;
	// The database of all LoginModel/AccountModel pairs, used for search bar results
	protected FacebookDatabase database;
	
	public BasicProfileController(T view, AccountModel dispAcc_model, 
			AccountModel myAcc_model, LoginModel login_model, FacebookDatabase database) {
		this.view = view;
		this.dispAcc_model = dispAcc_model;
		this.myAcc_model = myAcc_model;
		this.login_model = login_model;
		this.database = database;
			
		// Prevents own account from showing in search bar
		ArrayList<AccountModel> otherAccs = new ArrayList<AccountModel>(database.values());
		otherAccs.remove(myAcc_model);
		AccountModel[] accountsArr = (AccountModel[])otherAccs.toArray(new AccountModel[otherAccs.size()]);
		
		dispAcc_model.addPicObserver(this);
		
		view.setProfileName(dispAcc_model.getName());
		updatePic(myAcc_model.getPicturePath());
		view.setAllAccounts(accountsArr);
			
		view.addSettingsListener(new SettingsListener());
		view.addLogoutListener(new LogoutListener());
		view.addSearchListener(new SearchBarListener());
	}
	
	public void updatePic(String fileName) {
		try {
			Image picture = ImageIO.read(new File(fileName));
			view.setProfilePic(picture);
		}
		catch (IOException e) {
			System.out.println(e);
			System.out.println(e.getStackTrace());
		}
		
	}
	/**
	 * Creates and links FriendProfileView and FriendProfileController for given account
	 * One-line code given own method so that child classes may call it
	 * Only have to change this method if constructor changes
	 * 
	 * @param acc the AccountModel to open a profile for
	 */
	protected void openFriendProfile(AccountModel acc) {
		new FriendProfileController(new FriendProfileView(), acc,
				myAcc_model, login_model, database);
	}
	
	protected void openStrangerProfile(AccountModel acc) {
		new StrangerProfileController(new StrangerProfileView(), acc,
				myAcc_model, login_model, database);
	}
	
	/**
	 * Opens browser dialogue for selecting new image from file system
	 * Gets path from valid chosen picture and uses it to set myAcc_model's picture
	 */
	protected void selectPicture () {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.dir") + "/pics"));
		int result = fc.showOpenDialog(view);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fc.getSelectedFile();
			myAcc_model.setPicture(selectedFile.getPath());
		}
	}
	
	/**
	 * Helper method for SettingsListener displays deactivation dialogue
	 * If selected, calls deactivation methods and then exits
	 */
	protected void deactivateAccount() {
		int verify = JOptionPane.showConfirmDialog(view, "Are you sure you wish to deactivate your account?",
    			"Deactivation", JOptionPane.YES_NO_OPTION);
    	if (verify == JOptionPane.YES_OPTION) {
    		myAcc_model.deactivate();
    		
    		database.remove(login_model);
    		database.saveState();
    		
    		System.exit(0);
    	}
	}
	
	/**
	 * Displays a Window with 5 button options (including cancel)
	 * User can change their username/password/picture, deactivate their account, or do nothing
	 * @author Tim
	 *
	 */
	class SettingsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String[] options = new String[] {"Username", "Password", "Profile Picture",
					"Deactivate Account", "Cancel"};
			
		    int response = JOptionPane.showOptionDialog(view, "Select an option to change", "Settings",
		        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
		        null, options, options[4]);
		    
		    if (response == 0) {
		    	String username = JOptionPane.showInputDialog(view, "Set new username", login_model.getUsername());
		    	if (username != null)
		    		login_model.setUsername(username);
		    } else if (response == 1) {
		    	String password = JOptionPane.showInputDialog(view, "Set new password", login_model.getPassword());
		    	if (password != null)
		    		login_model.setPassword(password);
		    } else if (response == 2) {
		    	selectPicture();
		    } else if (response == 3) {
		    	deactivateAccount();
		    }
		}
	}
	
	/**
	 * Listener for logout button tells models to save state to files before exiting
	 * @author Tim
	 *
	 */
	class LogoutListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.showMessage("Logging you out");
			database.saveState();
			myAcc_model.saveState();
			System.exit(0);
		}
	}

	class SearchBarListener implements ActionListener {  
	 	public void actionPerformed(ActionEvent e) {
			AccountModel acc = view.getSearchAccount();
			if (myAcc_model.getFriends().contains(acc)) {
				openFriendProfile(acc);
			}
			else {
				openStrangerProfile(acc);
			}
		}
	}
}
