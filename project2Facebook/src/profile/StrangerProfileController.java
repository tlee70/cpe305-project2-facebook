package profile;

import login.LoginModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Extends BasicProfileController to add a button for following (becoming friends)
 * @author Tim
 *
 */
public class StrangerProfileController extends BasicProfileController<StrangerProfileView> {
	
	public StrangerProfileController(StrangerProfileView view, AccountModel strangerAcc_model, 
			AccountModel myAcc_model, LoginModel login_model, FacebookDatabase database) {
		super(view, strangerAcc_model, myAcc_model, login_model, database);

		view.setFollowListener(new FollowListener());
		
		view.pack();
		view.setVisible(true);
	}
	
	class FollowListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			myAcc_model.addFriend(dispAcc_model);
			view.dispose();
		}
	}
}
