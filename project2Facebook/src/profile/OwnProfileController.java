package profile;

import login.LoginModel;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

/**
 * Extends BasicProfileController to include wall, news feed, and friends list
 * myAcc_model is also used as dispAcc_model
 * @author Tim
 *
 */
public class OwnProfileController extends BasicProfileController<OwnProfileView> 
	implements WallObserver, NewsFeedObserver, FriendsListObserver {
	
	public OwnProfileController(OwnProfileView view, AccountModel myAcc_model, 
			LoginModel login_model, FacebookDatabase database) {
		super(view, myAcc_model, myAcc_model, login_model, database);
		
		myAcc_model.initialize(database.getAccounts());

		myAcc_model.addWallObserver(this);
		myAcc_model.addFeedObserver(this);
		myAcc_model.addFriendsListObserver(this);
		
		view.addWallListener(new MessagePostListener());
		view.addFriendPostListener(new FriendPostListener());
		view.addFriendsListener(new FriendsListListener());
		
		
		view.setFriendsList(myAcc_model.getFriends());
		
		String wall = myAcc_model.getWallPosts();
		if (wall!= null && wall.length() > 0) {
			view.appendWall(wall);
		}
		
		String feed = myAcc_model.getFeedPosts();
		if (feed != null && feed.length() > 0) {
			view.appendFeed(feed);
		}

		view.pack();
		view.setVisible(true);
	}
	
	public void updateWall(String message) {
		view.appendWall(message);
	}
	
	public void updateFeed(String message) {
		view.appendFeed(message);
	}
	
	public void updateFriendsList() {
		view.setFriendsList(myAcc_model.getFriends());
	}
	
	public void notifyFriendRemove(AccountModel acc) {
		view.getFriendsDefaultComboBoxModel().removeElement(acc);
	}
	
	public void notifyFriendAdd(AccountModel acc) {
		view.getFriendsDefaultComboBoxModel().addElement(acc);
	}
	
	class MessagePostListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String message = view.getPost();
			
			myAcc_model.post(message);
		}
	}
	
	/**
	 * Opens a dialog for posting as a friend
	 * @author Tim
	 *
	 */
	class FriendPostListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JList<AccountModel> friendsJList = new JList<AccountModel>();
			friendsJList.setModel(view.getFriendsDefaultComboBoxModel());
			JScrollPane scrollFriends = new JScrollPane(friendsJList);
			scrollFriends.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollFriends.setPreferredSize(new Dimension(100,75));
			JTextField field = new JTextField();
			
			Object[] message = {scrollFriends, field};
			
			int option = JOptionPane.showConfirmDialog(view, message, "Post as a friend", JOptionPane.OK_CANCEL_OPTION);
			
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
	
	/**
	 * calls openFriendProfile() when a friend is selected from the friends list in the view
	 * @author Tim
	 *
	 */
	class FriendsListListener implements ListSelectionListener {  
	 	public void valueChanged(ListSelectionEvent e) {
	 		if ( !e.getValueIsAdjusting() ) { 
	 			AccountModel acc = view.getFriendListSelection();
	 			if (acc != null) {
	 				openFriendProfile(acc);
	 			}
	 		}
		}
	}

}
