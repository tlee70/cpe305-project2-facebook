package profile;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import login.LoginModel;

public class FriendProfileController extends AbstractProfileController<FriendProfileView> 
	implements WallObserver, PictureObserver {
	
	public FriendProfileController(FriendProfileView view, AccountModel friendAcc_model, 
			AccountModel myAcc_model, LoginModel login_model, List<AccountModel> accounts) {
		super(view, friendAcc_model, myAcc_model, login_model, accounts);
		
		friendAcc_model.addPicObserver(this);
		friendAcc_model.addWallObserver(this);
		
		view.setUnfollowListener(new UnfollowListener());
		
		view.appendWall(friendAcc_model.getWall().toString());
		
		view.pack();
		view.setVisible(true);
	}

	public void updateWall(String message) {
		view.appendWall(message);
	}
	
	public void updatePic(Image image) {
		view.setProfilePic(image);
	}

	class UnfollowListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.dispose();
			myAcc_model.removeFriend(dispAcc_model);
		}
	}
}
