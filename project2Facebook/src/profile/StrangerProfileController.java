package profile;

import login.LoginModel;

import java.util.List;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class StrangerProfileController extends AbstractProfileController<StrangerProfileView> {

	private AccountModel strangerAcc_model;
	
	public StrangerProfileController(StrangerProfileView view, AccountModel strangerAcc_model, 
			AccountModel myAcc_model, LoginModel login_model, List<AccountModel> accounts) {
		super(view, myAcc_model, login_model, accounts);
		
		this.strangerAcc_model = strangerAcc_model;
		
		view.setFollowListener(new FollowListener());
		
		view.setVisible(true);
	}
	
	class FollowListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			myAcc_model.addFriend(strangerAcc_model);
		}
	}
}
