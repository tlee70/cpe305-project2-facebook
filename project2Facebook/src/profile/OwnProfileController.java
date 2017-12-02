package profile;

import login.LoginModel;

import java.util.List;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Image;

public class OwnProfileController implements PictureObserver, 
			WallObserver, NewsFeedObserver, FriendsListObserver {

	private OwnProfileView op_view;
	private AccountModel acc_model;
	//private Map<LoginModel, AccountModel> database;
	private LoginModel login_model;
	//private List<AccountModel> accounts;
	
	public OwnProfileController(OwnProfileView op_view, AccountModel acc_model, LoginModel login_model, List<AccountModel> accounts) {
	//public OwnProfileController(OwnProfileView op_view, AccountModel acc_model, Map<LoginModel, AccountModel> database) {
		this.op_view = op_view;
		this.acc_model = acc_model;
		//this.database = database;
		
		acc_model.addPicObserver(this);
		acc_model.addWallObserver(this);
		acc_model.addFeedObserver(this);
		acc_model.addFriendsListObserver(this);
		
		// Prevents own account from showing in search bar
		//List<AccountModel> allAccounts = new ArrayList<AccountModel>(database.values());
		this.login_model = login_model;
		//this.accounts = accounts;
		accounts.remove(acc_model);
		AccountModel[] accountsArr = (AccountModel[])accounts.toArray(new AccountModel[accounts.size()]);
		
		op_view.setProfileName(acc_model.getName());
		op_view.setProfilePic(acc_model.getPicture());
		op_view.setFriendsList(acc_model.getFriends());
		op_view.setAllAccounts(accountsArr);
		
		op_view.addSettingsListener(new SettingsListener());
		op_view.addLogoutListener(new LogoutListener());
		op_view.addWallListener(new MessagePostListener());
		op_view.addFriendPostListener(new FriendPostListener());
		op_view.addSearchListener(new SearchBarListener());
		op_view.addFriendsListener(new FriendsListListener());

		
		op_view.setVisible(true);
	}
	
	public void updateWall(String message) {
		op_view.appendWall(message);
	}
	
	public void updatePic(Image image) {
		op_view.setProfilePic(image);
	}
	
	public void updateFeed(String message) {
		op_view.appendFeed(message);
	}
	
	public void updateFriendsList() {
		op_view.setFriendsList(acc_model.getFriends());
	}
	
	public void notifyFriendRemove(AccountModel acc) {
		op_view.getFriendsDefaultComboBoxModel().addElement(acc);
	}
	
	public void notifyFriendAdd(AccountModel acc) {
		op_view.getFriendsDefaultComboBoxModel().removeElement(acc);
	}
	
	public Image selectPicture() {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("~/sbh"));
		int result = fc.showOpenDialog(op_view);
		
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
	
	class MessagePostListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String message = op_view.getPost();
			
			acc_model.post(message);
		}
	}
	
	class SettingsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String[] options = new String[] {"Username", "Password", "Profile Picture", "Deactivate Account", "Cancel"};
		    int response = JOptionPane.showOptionDialog(op_view, "Select an option to change", "Settings",
		        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
		        null, options, options[4]);
		    
		    if (response == 0) {
		    	String username = JOptionPane.showInputDialog(op_view, "Set new username", login_model.getUsername());
		    	if (username != null)
		    		login_model.setUsername(username);
		    } else if (response == 1) {
		    	String password = JOptionPane.showInputDialog(op_view, "Set new password", login_model.getPassword());
		    	if (password != null)
		    		login_model.setPassword(password);
		    } else if (response == 2) {
		    	acc_model.setPicture(selectPicture());
		    } else if (response == 3) {
		    	// Deactivate account
		    }
			
		}
	}
	
	class FriendPostListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JList<AccountModel> friendsJList = new JList<AccountModel>();
			friendsJList.setModel(op_view.getFriendsDefaultComboBoxModel());
			JScrollPane scrollFriends = new JScrollPane(friendsJList);
			scrollFriends.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollFriends.setPreferredSize(new Dimension(100,75));
			JTextField field = new JTextField();
			
			Object[] message = {scrollFriends, field};
			
			int option = JOptionPane.showConfirmDialog(op_view, message, "Post as a friend", JOptionPane.OK_CANCEL_OPTION);
			
			if (option == JOptionPane.OK_OPTION) {
			    String text = field.getText();
			    if (text != null && text.length() > 0 ) {
			    	AccountModel friend = friendsJList.getSelectedValue();
			    	if (friend != null) {
			    		friend.post(text);
			    	}
			    }	
			} 
		}
	}
	
	class LogoutListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			acc_model.saveState();
			op_view.showMessage("Logging you out");
			System.exit(0);
		}
	}

	class SearchBarListener implements ActionListener {  
	 	public void actionPerformed(ActionEvent e) {
			op_view.showMessage("Search Bar functionality not yet implemented");
		}
	}
	
	class FriendsListListener implements ListSelectionListener {  
	 	public void valueChanged(ListSelectionEvent e) {
	 		op_view.showMessage("Friend List functionality not yet implemented");
		}
	}

}
