package profile;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public abstract class AbstractProfileView extends JFrame {

	protected static final int PICTURE_WIDTH = 150;
	protected static final int PICTURE_HEIGHT = 150;
	protected static final Dimension WALL_SIZE = new Dimension(100,150);
	
	protected JPanel content;
	protected JLabel namePicLbl;
	protected JComboBox<AccountModel> searchBar;
	protected JButton settingsBtn = new JButton("Settings");
	protected JButton logoutBtn = new JButton("Logout");
	
	public AbstractProfileView() {
		content = new JPanel();
		content.setLayout(new GridBagLayout());
		this.setContentPane(content);
		
		displayNamePicLbl();
		displaySearchBar();
		displayButtons();
	}
	
	private void displayNamePicLbl() {
		namePicLbl = new JLabel("placeholder name", null, JLabel.CENTER);
		namePicLbl.setFont(new Font("Serif", Font.PLAIN, 16));
		namePicLbl.setHorizontalTextPosition(JLabel.CENTER);
		namePicLbl.setVerticalTextPosition(JLabel.BOTTOM);
		namePicLbl.setPreferredSize(new Dimension(PICTURE_WIDTH, PICTURE_HEIGHT+50));
		
		GridBagConstraints labelConstraints = new GridBagConstraints();
		labelConstraints.gridx = 0;
		labelConstraints.gridy = 0;
		labelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		labelConstraints.fill = GridBagConstraints.BOTH;
		labelConstraints.gridheight = 2;
		labelConstraints.weightx = 0;
		labelConstraints.weighty = 0;
		content.add(namePicLbl, labelConstraints);
	}
	
	private void displaySearchBar() {
		searchBar = new JComboBox<AccountModel>();
		searchBar.setEditable(false);
		//AutoCompleteDecorator.decorate(searchBar);
		
		GridBagConstraints searchBarConstraints = new GridBagConstraints();
		searchBarConstraints.gridx = 1;
		searchBarConstraints.gridy = 0;
		searchBarConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		searchBarConstraints.weightx = 0;
		searchBarConstraints.weighty = 0;
		content.add(searchBar, searchBarConstraints);
	}
	
	private void displayButtons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(settingsBtn);
		buttonPanel.add(logoutBtn);
		
		GridBagConstraints buttonConstraints = new GridBagConstraints();
		buttonConstraints.gridx = 2;
		buttonConstraints.gridy = 0;
		buttonConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		buttonConstraints.weightx = 0;
		buttonConstraints.weighty = 0;
		content.add(buttonPanel, buttonConstraints);
	}
	
		
	public void setProfileName(String name) {
		namePicLbl.setText(name);
	}
	
	public void setProfilePic(Image image) {
		Image dimg = image.getScaledInstance(PICTURE_WIDTH, PICTURE_HEIGHT, Image.SCALE_SMOOTH);
		namePicLbl.setIcon(new ImageIcon(dimg));
	}
	
	public void setAllAccounts(AccountModel[] accounts) {
		searchBar.setModel(new DefaultComboBoxModel<AccountModel>(accounts));
	}
	
	public void addSearchListener(ActionListener listener) {
		searchBar.addActionListener(listener);
	}
	
	public AccountModel getSearchAccount() {
		return (AccountModel)searchBar.getSelectedItem();
	}
	
	public void addSettingsListener(ActionListener listener) {
		settingsBtn.addActionListener(listener);
	}

	public void addLogoutListener(ActionListener listener) {
		logoutBtn.addActionListener(listener);
	}

	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
}
