package login;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.Map;
import java.util.ArrayList;

import profile.AccountModel;
import profile.OwnProfileView;
import profile.OwnProfileController;

public class LoginController {
	private LoginView li_view;
	private Map<LoginModel, AccountModel> accounts;
	
	public LoginController(LoginView view, Map<LoginModel, AccountModel> accounts) {
		this.li_view = view;
		this.accounts = accounts;
		
		view.addEnterListener(new LoginListener());
		view.setVisible(true);
	}
	
	class LoginListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			LoginModel attemptedLogin = new LoginModel(li_view.getUsername(), li_view.getPassword());
			
			if (accounts.containsKey(attemptedLogin)) {
				li_view.dispose();
				new OwnProfileController(new OwnProfileView(), accounts.get(attemptedLogin), 
						accounts);
			}
			else {
				li_view.showError("Incorrect login information");
			}
		}
	}
}
