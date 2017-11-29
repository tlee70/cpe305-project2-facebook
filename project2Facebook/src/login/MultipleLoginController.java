package login;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.Map;

import profile.AccountModel;
import profile.OwnProfileView;
import profile.OwnProfileController;

public class MultipleLoginController {
	private LoginView li_view;
	private Map<MultipleLoginModel, AccountModel> accounts;
	
	public MultipleLoginController(LoginView view, Map<MultipleLoginModel, AccountModel> accounts) {
		this.li_view = view;
		this.accounts = accounts;
		
		view.addEnterListener(new LoginListener());
		view.setVisible(true);
	}
	
	class LoginListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			MultipleLoginModel attemptedLogin = new MultipleLoginModel(li_view.getUsername(), li_view.getPassword());
			
			if (accounts.containsKey(attemptedLogin)) {
				li_view.dispose();
				new OwnProfileController(new OwnProfileView(), accounts.get(attemptedLogin));
			}
			else {
				li_view.showError("Incorrect login information");
			}
		}
	}
}
