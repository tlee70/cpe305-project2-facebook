package profile;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.Image;

public class OwnProfileController implements PictureObserver, WallObserver, NewsFeedObserver {

	private OwnProfileView op_view;
	private AccountModel acc_model;
	
	public OwnProfileController(OwnProfileView op_view, AccountModel acc_model) {
		this.op_view = op_view;
		this.acc_model = acc_model;
		
		acc_model.addPicObserver(this);
		acc_model.addWallObserver(this);
		acc_model.addFeedObserver(this);
		
		// initialize listeners and other components
		op_view.setProfileName(acc_model.getName());
		op_view.setProfilePic(acc_model.getPicture());
		op_view.addSettingsListener(new SettingsListener());
		op_view.addLogoutListener(new LogoutListener());
		op_view.addWallListener(new MessagePostListener());
		op_view.addFriendPostListener(new FriendPostListener());
		op_view.setFriendsList(acc_model.getFriends());
		
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
			Object[] values = op_view.simulateFriendPost();
			
			if (values != null) {
				AccountModel friend = (AccountModel) values[0];
				String message = (String) values[1];
				
				friend.post(message);
			}
		}
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
