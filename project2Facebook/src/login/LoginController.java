package login;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import profile.AccountModel;
import profile.OwnProfileView;
import profile.OwnProfileController;

public class LoginController {
	private LoginView li_view;
	private LoginModel li_model;
	private AccountModel account;
	
	public LoginController(LoginView view, LoginModel model, AccountModel account) {
		this.li_view = view;
		this.li_model = model;
		this.account = account;
		
		view.addEnterListener(new LoginListener());
		view.setVisible(true);
	}
	
	class LoginListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String username = li_view.getUsername();
			String password = li_view.getPassword();
			if (li_model.verify(username, password)) {
				li_view.dispose();
				new OwnProfileController(new OwnProfileView(), account);
			}
			else {
				li_view.showError("Incorrect login information");
			}
		}
	}
}