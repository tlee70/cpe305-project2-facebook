package profile;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.DefaultListModel;

import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Image;

public class OwnProfileController implements PictureObserver, 
			WallObserver, NewsFeedObserver, FriendsListObserver {

	private OwnProfileView op_view;
	private AccountModel acc_model;
	
	public OwnProfileController(OwnProfileView op_view, AccountModel acc_model) {
		this.op_view = op_view;
		this.acc_model = acc_model;
		
		acc_model.addPicObserver(this);
		acc_model.addWallObserver(this);
		acc_model.addFeedObserver(this);
		acc_model.addFriendsListObserver(this);
		
		
		op_view.setProfileName(acc_model.getName());
		op_view.setProfilePic(acc_model.getPicture());
		op_view.setFriendsList(acc_model.getFriends());
		
		op_view.addSettingsListener(new SettingsListener());
		op_view.addLogoutListener(new LogoutListener());
		op_view.addWallListener(new MessagePostListener());
		op_view.addFriendPostListener(new FriendPostListener());

		
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
		op_view.getFriendsDefaultListModel().addElement(acc);
	}
	
	public void notifyFriendAdd(AccountModel acc) {
		op_view.getFriendsDefaultListModel().removeElement(acc);
	}
	
	public Image selectPicture() {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("~/sbh"));
		int result = fc.showOpenDialog(new JFrame());
		
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
		
		op_view.showMessage("Error in select new profile picture");
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
			// Required: Change profile pic
			acc_model.setPicture(selectPicture());
			
			/** Optional:
			 * Change username
			 * Change password
			 * Deactivate account
			 */
		}
	}
	
	class FriendPostListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JList<AccountModel> friendsJList = new JList<AccountModel>();
			friendsJList.setModel(op_view.getFriendsDefaultListModel());
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
/**
 * Phase 2 functionality
	class SearchBarListener implements ActionListener {  
	 	public void actionPerformed(ActionEvent e) {
			// autocomplete? display all possible matches in separate window? 
		}
	}
	
	class FriendListListener implements ActionListener {  
	 	public void actionPerformed(ActionEvent e) {
			
		}
	}
 */
}
