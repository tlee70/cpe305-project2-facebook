package login;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * A basic login screen
 * 
 * @author Tim
 *
 */
public class LoginView extends JFrame{
	
	private JTextField usernameTf = new JTextField(15);
	//private JPasswordField passwordTf = new JPasswordField(20);
	private JTextField passwordTf = new JTextField(20);
	private JButton enterBtn = new JButton("Enter");
	
	public LoginView () {
		JPanel content = new JPanel();
		content.setLayout(new GridLayout(0,2));
		content.add(new JLabel("Username"));
		content.add(usernameTf);
		content.add(new JLabel("Password"));
		content.add(passwordTf);
		content.add(enterBtn);
		this.getRootPane().setDefaultButton(enterBtn);
		
		this.setContentPane(content);
		this.pack();
		
		this.setTitle("Facebook Login");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public String getUsername() {
		return usernameTf.getText();
	}
	
	public String getPassword() {
		return passwordTf.getText();
	}
	
	public void addEnterListener(ActionListener eal) {
		enterBtn.addActionListener(eal);
	}
	
	public void showError(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}

}
