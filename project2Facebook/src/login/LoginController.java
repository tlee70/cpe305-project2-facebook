package login;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginController {
	private LoginView li_view;
	private LoginModel li_model;
	
	public LoginController(LoginView view, LoginModel model) {
		this.li_view = view;
		this.li_model = model;
		
		view.addEnterListener(new LoginListener());
		view.setVisible(true);
	}
	
	class LoginListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String username = li_view.getUsername();
			String password = li_view.getPassword();
			if (li_model.verify(username, password)) {
				li_view.setVisible(false);
				
				// Show OwnProfileView
				System.out.println("Successful login");
				
				li_view.dispose();
			}
			else {
				li_view.showError("Incorrect login information");
			}
		}
	}
}
