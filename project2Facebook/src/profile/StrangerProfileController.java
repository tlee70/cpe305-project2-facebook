package profile;

import login.LoginModel;

import java.util.List;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class StrangerProfileController extends AbstractProfileController<StrangerProfileView> 
	implements PictureObserver {
	
	public StrangerProfileController(StrangerProfileView view, AccountModel strangerAcc_model, 
			AccountModel myAcc_model, LoginModel login_model, List<AccountModel> accounts) {
		super(view, strangerAcc_model, myAcc_model, login_model, accounts);
	
		strangerAcc_model.addPicObserver(this);
		
		view.setFollowListener(new FollowListener());
		
		view.pack();
		view.setVisible(true);
	}
	
	public void updatePic(Image image) {
		view.setProfilePic(image);
	}
	
	class FollowListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			myAcc_model.addFriend(dispAcc_model);
			view.dispose();
		}
	}
}
