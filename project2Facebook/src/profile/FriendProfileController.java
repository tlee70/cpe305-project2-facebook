package profile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import login.LoginModel;

/**
 * Extension of BasicProfileController for controlling display of friend's wall and unfollow button
 * @author Tim
 *
 */
public class FriendProfileController extends BasicProfileController<FriendProfileView> 
	implements WallObserver{
	
	public FriendProfileController(FriendProfileView view, AccountModel friendAcc_model, 
			AccountModel myAcc_model, LoginModel login_model, FacebookDatabase database) {
		super(view, friendAcc_model, myAcc_model, login_model, database);
		
		friendAcc_model.addWallObserver(this);
		
		view.setUnfollowListener(new UnfollowListener());
		
		String wall = friendAcc_model.getWallPosts();
		if (wall!= null && wall.length() > 0) {
			view.appendWall(wall);
		}
		
		view.pack();
		view.setVisible(true);
	}

	public void updateWall(String message) {
		view.appendWall(message);
	}

	class UnfollowListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.dispose();
			myAcc_model.removeFriend(dispAcc_model);
		}
	}
}
