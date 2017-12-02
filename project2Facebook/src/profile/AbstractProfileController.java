package profile;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import login.LoginModel;

public abstract class AbstractProfileController<T extends AbstractProfileView> {
	
	protected T view;
	protected AccountModel myAcc_model;
	protected AccountModel dispAcc_model;
	protected LoginModel login_model;
	protected List<AccountModel> accounts;
	
	public AbstractProfileController(T view, AccountModel dispAcc_model, 
			AccountModel myAcc_model, LoginModel login_model, List<AccountModel> accounts) {
		this.view = view;
		this.dispAcc_model =  dispAcc_model;
		this.myAcc_model = myAcc_model;
		this.login_model = login_model;
		this.accounts = accounts;
			
		// Prevents own account from showing in search bar
		accounts.remove(myAcc_model);
		AccountModel[] accountsArr = (AccountModel[])accounts.toArray(new AccountModel[accounts.size()]);
			
		view.setProfileName(dispAcc_model.getName());
		view.setProfilePic(dispAcc_model.getPicture());
		view.setAllAccounts(accountsArr);
			
		view.addSettingsListener(new SettingsListener());
		view.addLogoutListener(new LogoutListener());
		view.addSearchListener(new SearchBarListener());
	}
	
	protected void openStrangerProfile(AccountModel stranger) {
		new StrangerProfileController(new StrangerProfileView(), stranger, myAcc_model, 
				login_model, accounts);
	}
	
	protected void openFriendProfile(AccountModel friend) {
		new FriendProfileController(new FriendProfileView(), friend, myAcc_model, 
				login_model, accounts);
	}
	
	public Image selectPicture() {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("~/sbh"));
		int result = fc.showOpenDialog(view);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fc.getSelectedFile();
			try {
				Image image = ImageIO.read(selectedFile);
				return image;
			}
			catch (IOException exception) {
				System.out.println(exception.getStackTrace());
			}
		}
		else if (result == JFileChooser.CANCEL_OPTION) {
			System.out.println("Image selection canceled");
		}
		
		return null;
	}
	
	class SettingsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String[] options = new String[] {"Username", "Password", "Profile Picture", "Deactivate Account", "Cancel"};
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
		    	myAcc_model.setPicture(selectPicture());
		    } else if (response == 3) {
		    	// Deactivate account
		    }
			
		}
	}
	
	class LogoutListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			myAcc_model.saveState();
			view.showMessage("Logging you out");
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
