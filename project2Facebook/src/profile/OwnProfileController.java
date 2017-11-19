package profile;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OwnProfileController {

	private OwnProfileView op_view;
	private AccountModel acc_model;
	
	public OwnProfileController(OwnProfileView op_view, AccountModel acc_model) {
		this.op_view = op_view;
		this.acc_model = acc_model;
	}
	
	class MessagePostListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String message = op_view.getPost();
			
			acc_model.post(message);
		}
	}
	
	class SettingsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Change profile pic
			
			// Change username
			
			// Change password
			
			// Deactivate account
		}
	}
	
/**
 * Phase 2 functionality
	class SearchBarListener implements ActionListener {  
	 	public void actionPerformed(ActionEvent e) {
			// autocomplete? display all possible matches in separate window? 
		}
	}
	
	class FriendListListener implements ActionListener {  
	 	public void actionPerformed(ActionEvent e) {
			
		}
	}
 */
}
