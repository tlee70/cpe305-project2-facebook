package profile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import login.LoginModel;

public abstract class AbstractProfileController<T extends AbstractProfileView> {
	
	protected T view;
	protected AccountModel myAcc_model;
	protected AccountModel dispAcc_model;
	protected LoginModel login_model;
	protected FacebookDatabase accounts;
	
	public AbstractProfileController(T view, AccountModel dispAcc_model, 
			AccountModel myAcc_model, LoginModel login_model, FacebookDatabase accounts) {
		this.view = view;
		this.dispAcc_model = dispAcc_model;
		
		/**if ( !myAcc_model.equals(accounts.get(login_model)) ) {
			System.out.println("Discepancy between given account and account retrieved w/ login");
			System.out.println(myAcc_model);
			System.out.println(login_model.getUsername() + ", " + login_model.getPassword());
			System.out.println(accounts.get(login_model));
			myAcc_model = accounts.get(login_model);
		} */
		this.myAcc_model = myAcc_model;
		this.login_model = login_model;
		this.accounts = accounts;
			
		// Prevents own account from showing in search bar
		ArrayList<AccountModel> otherAccs = new ArrayList<AccountModel>(accounts.values());
		otherAccs.remove(myAcc_model);
		AccountModel[] accountsArr = (AccountModel[])otherAccs.toArray(new AccountModel[otherAccs.size()]);
			
		view.setProfileName(dispAcc_model.getName());
		view.setProfilePic(dispAcc_model.getPicture());
		view.setAllAccounts(accountsArr);
			
		view.addSettingsListener(new SettingsListener());
		view.addLogoutListener(new LogoutListener());
		view.addSearchListener(new SearchBarListener());
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
				myAcc_model, login_model, accounts);
	}
	
	protected void openStrangerProfile(AccountModel acc) {
		new StrangerProfileController(new StrangerProfileView(), acc,
				myAcc_model, login_model, accounts);
	}
	
	protected void selectPicture () {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.dir") + "/pics"));
		int result = fc.showOpenDialog(view);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fc.getSelectedFile();
			myAcc_model.setPicture(selectedFile.getPath());
		}
	}
		
	class SettingsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String[] options = new String[] {"Username", "Password", "Profile Picture", "Cancel"};
		    int response = JOptionPane.showOptionDialog(view, "Select an option to change", "Settings",
		        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
		        null, options, options[3]);
		    
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
		    } 
			
		}
	}
	
	class LogoutListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.showMessage("Logging you out");
			accounts.saveState();
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
